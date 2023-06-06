package cbs.cine_foro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import jakarta.annotation.PostConstruct;

@SpringBootTest
public class UserServiceImplTest {

    // TODO: Check the exceptions
    
    @Autowired
    private IUserService service;

    private static List<User> testUsers;

    @PostConstruct
    void createUser() {
        testUsers = List.of(
            new User("Tazón de chocolate"),
            new User("Pirolito"),
            new User("Lipichún")
        );
        //testUser = new User("Tazón de chocolate");
    }

    @Test
    void testSaveUserCorrect() throws UserAlreadyExistsException {
        for (int i = 0; i < testUsers.size(); i++) {
            User result = service.saveUser(testUsers.get(i));
            assertEquals(testUsers.get(i).getName(), result.getName());
            testUsers.set(i, result);
        }        
    }

    // exception already exists
    @Test
    void testSaveUserExisting() {
        for (int i = 0; i < testUsers.size(); i++) {
            final int index = i;
            assertThrows(UserAlreadyExistsException.class,
                        () -> service.saveUser(testUsers.get(index)));
        }
    }

    @Test
    void testGetAllUsers() {
        List<User> users = service.getAllUsers();
        System.out.println(users);
        assertTrue(users.size() > 0);
    }

    @Test
    void testGetUserById() throws UserNotExistsException {
        final Long idToCheck = 1L;
        User result = service.getUserById(idToCheck);
        assertNotNull(result);
        assertEquals(idToCheck, result.getUserId());
        System.out.println(result);
    }

    @Test
    void testGetUserByIdNoExist() {
        final Long idToCheck = 15554L;
        assertThrows(UserNotExistsException.class, 
                    () -> service.getUserById(idToCheck));  
    }

    @Test
    void testGetUserByName() throws UserNotExistsException {
        User result = service.getUserByName(testUsers.get(0).getName());
        assertNotNull(result);
        assertEquals(testUsers.get(0).getName(), result.getName());
        System.out.println(result);
    }

    @Test
    void testGetUserByNameNotExist() {
        final String nameToCheck = "asdaAwedqd aDad ada 51da54ads";
        assertThrows(UserNotExistsException.class, 
                    () -> service.getUserByName(nameToCheck));
    }
    
    @Test
    void testUpdateUserName() throws UserNotExistsException {
        User result = service.updateUserName(testUsers.get(0).getName(), "New name for the user");
        User expected = service.getUserById(result.getUserId());
        assertEquals(expected, result);
    }
    
    @Test
    void testUpdateUserNameNotExist() {
        final String name = "Asdad asd asd asd asd qe213";
        final String newName = "Should not update";
        assertThrows(UserNotExistsException.class, 
                    () -> service.updateUserName(name, newName));           
    }


    

    
}
