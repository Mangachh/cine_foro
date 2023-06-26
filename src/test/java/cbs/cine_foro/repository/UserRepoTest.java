package cbs.cine_foro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cbs.cine_foro.entity.User;

//@SpringBootTest
@DataJpaTest
public class UserRepoTest {

    @Autowired
    private UserRepo repo;

    // methods to populate arguments

    static Stream<String> provideUniqueUsersNames() {
        return Stream.of("Pepote", "Mariela", "25n25", "Originaliu", "Pipo");
    }

    static Stream<Arguments> provideUniqueUsersNamesWithId() {
        return Stream.of(
                Arguments.of(1L, "Pepote"),
                Arguments.of(2L, "Mariela"),
                Arguments.of(3L, "Silifonte"));
    }
    
    
    @ParameterizedTest
    @MethodSource("provideUniqueUsersNames")
    void saveUserTestCorrect(String name) {
        User user = new User(name);
        User result = repo.save(user);
        assertEquals(user.getName(), result.getName());
        System.out.println("Added user: " + user.toString());
    }

    @ParameterizedTest
    @MethodSource("provideUniqueUsersNamesWithId")
    void getUserById(Long id, String name) {
        User saved = repo.save(new User(id, name));
        User end = repo.findById(saved.getUserId()).orElse(null);

        assertNotNull(end);
        assertEquals(saved, end);        
    }

    // borramos user
    @ParameterizedTest
    @MethodSource("provideUniqueUsersNamesWithId")
    void removeUserByIdCorrect(Long id, String name) {
        
        User saved = repo.save(new User(id, name));
        User end = repo.findById(saved.getUserId()).orElse(null);

        // chekeamos que está
        assertNotNull(end);
        repo.deleteById(saved.getUserId());
        end = repo.findById(saved.getUserId()).orElse(null);

        assertNull(end);
    }

}
