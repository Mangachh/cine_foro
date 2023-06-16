package cbs.cine_foro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.repository.UserRepo;
import jakarta.annotation.PostConstruct;

@SpringBootTest
public class UserServiceImplTest {
    // TODO: change to mockito?

    @Autowired
    private IUserService service;

    @MockBean
    private UserRepo repo;

    private static List<User> testUsers;

    @PostConstruct
    void createUser() {
        testUsers = List.of(
                new User(1L,"Tazón de chocolate"),
                new User(2L, "Pirolito"),
                new User(3L, "Lipichún"));
    }

    @Test
    void testSaveUserCorrect() throws UserAlreadyExistsException {
        for (int i = 0; i < testUsers.size(); i++) {
            Mockito.when(repo.save(testUsers.get(i)))
                    .thenReturn(testUsers.get(i));

            User result = service.saveUser(testUsers.get(i));
            assertEquals(testUsers.get(i).getName(), result.getName());
        }
    }

    // exception already exists
    @Test
    void testSaveUserExisting() {

        Mockito.when(repo.save(testUsers.get(0)))
                .thenAnswer(i -> {
                    throw new DataIntegrityViolationException("Data integrity error");}); // aquí va el jpa exception ese

        assertThrows(UserAlreadyExistsException.class,
                () -> service.saveUser(testUsers.get(0)));

    }    

    @Test
    void testGetAllUsers() {
        Mockito.when(repo.findAll())
                .thenReturn(testUsers);

        List<User> users = service.getAllUsers();
        System.out.println(users);
        assertTrue(users.size() > 0);
    }

    @Test
    void testGetUserById() throws UserNotExistsException {
        final Long idToCheck = testUsers.get(0).getUserId();
        Mockito.when(repo.findById(idToCheck))
                .thenReturn(Optional.of(testUsers.get(0)));

        User result = service.getUserById(idToCheck);
        assertNotNull(result);
        assertEquals(idToCheck, result.getUserId());
        System.out.println(result);
    }

    @Test
    void testGetUserByIdNoExist() {
        final Long idToCheck = 15554L;
        Mockito.when(repo.findById(idToCheck))
                .thenReturn(Optional.empty());

        assertThrows(UserNotExistsException.class,
                () -> service.getUserById(idToCheck));
    }

    @Test
    void testGetUserByName() throws UserNotExistsException {
        Mockito.when(repo.findByName(testUsers.get(0).getName()))
                .thenReturn(Optional.of(testUsers.get(0)));

        User result = service.getUserByName(testUsers.get(0).getName());
        assertNotNull(result);
        assertEquals(testUsers.get(0).getName(), result.getName());
        System.out.println(result);
    }

    @Test
    void testGetUserByNameNotExist() {
        final String nameToCheck = "asdaAwedqd aDad ada 51da54ads";
        Mockito.when(repo.findByName(nameToCheck))
                .thenReturn(Optional.empty());

        assertThrows(UserNotExistsException.class,
                () -> service.getUserByName(nameToCheck));
    }

    @Test
    void testUpdateUserName() throws UserNotExistsException {
        final String newName = "New name for the user";

        Mockito.when(repo.findByName(testUsers.get(0).getName()))
                .thenReturn(Optional.of(testUsers.get(0)));

        Mockito.when(repo.save(testUsers.get(0)))
                .thenReturn(testUsers.get(0));

        User result = service.updateUserName(testUsers.get(0).getName(), newName);
        //User expected = service.getUserById(result.getUserId());
        assertEquals(result.getName(), newName);
    }

    @Test
    void testUpdateUserNameNotExist() {
        final String name = "Asdad asd asd asd asd qe213";
        final String newName = "Should not update";
        Mockito.when(repo.findByName(name))
            .thenReturn(Optional.empty());
            
        assertThrows(UserNotExistsException.class,
                () -> service.updateUserName(name, newName));
    }
}
