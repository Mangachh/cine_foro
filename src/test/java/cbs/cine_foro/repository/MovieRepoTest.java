package cbs.cine_foro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.h2.result.ResultTarget;
import org.hibernate.TransientPropertyValueException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.event.annotation.AfterTestClass;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import jakarta.annotation.PostConstruct;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@SpringBootTest
// @DataJpaTest
public class MovieRepoTest {
    @Autowired
    private MovieRepo repo;

    private User user;
    private Long userId;

    @Autowired
    private UserRepo userRepo;

    private List<Movie> movies;

    @PostConstruct
    void setUp() {
        user = new User("Test User");
        try {
            user = userRepo.save(user);
            // userId = user.getUserId()
        } catch (Exception e) {
            System.out.println(userRepo.findAll());
            user = userRepo.findById(1L).get();
        }

        this.setMoviesWithUser();

    }

    @AfterTestClass
    void clearUp() {
        repo.deleteAll(movies);
        userRepo.delete(user);
    }

    private void setMoviesWithUser() {
        Movie a = Movie.builder()
                .originalTitle("Fingers, fingers, fingers")
                .spanishTitle("Dedazos a porrazos")
                .proposedDate(LocalDate.now())
                .build();
        a.setUserProposed(user);

        Movie b = Movie.builder()
                .originalTitle("Tip Tap Top")
                .spanishTitle("Los misteros de John")
                .proposedDate(LocalDate.now())
                .build();
        b.setUserProposed(user);
        movies = List.of(a, b);
    }

    @Test
    @Order(1)
    void saveMovieWithUser() {
        for (Movie m : movies) {
            Movie result = repo.save(m);
            assertEquals(m, result);
        }
    }

    @Test
    @Order(2)
    void findMoviesByUser() {
        List<Movie> resultList = repo.findByUserProposed(user);
        System.out.println("----------------------------");
        System.out.println(movies);
        System.out.println(user);
        System.out.println(resultList);
        assertTrue(resultList.size() == 2);
    }

    @Test
    @Order(3)
    void getMovieById() {
        movies = repo.findAll();
        for (Movie m : movies) {
            Optional<Movie> result = repo.findById(m.getMovieId());
            assertTrue(result.isPresent());
            assertEquals(m, result.get());
        }
    }

    @Test
    @Order(4)
    void removeMovie() {
        Movie toDelete = repo.findById(1L).get();
        repo.delete(toDelete);
        assertTrue(repo.findById(1L).isEmpty());
    }

    @Test
    @Order(5)
    void saveMovieUserNotExists() {
        User u = new User("Not exists");

        assertTrue(userRepo.findByName(u.getName()).isEmpty());

        Movie toSave = Movie.builder()
                .originalTitle("aaaaa")
                .spanishTitle("Hola caracola")
                .build();
        toSave.setUserProposed(u);

        // exception, because the user doesn't exist
        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> repo.save(toSave));
        List<Movie> dataMovie = repo.findAll();

        // checking the list, just in case
        System.out.println("Movies: " + dataMovie.toString());
    }

    @Test
    @Order(6)
    void saveMovieUserNull() {
        Movie toSave = Movie.builder()
                .originalTitle("aaaaa")
                .spanishTitle("Hola caracola")
                .build();
        
        // error, userProposed can't be null
        assertThrows(DataIntegrityViolationException.class,
                    () -> repo.save(toSave));
        List<Movie> dataMovie = repo.findAll();

        // checking the list, just in case
        System.out.println("Movies: " + dataMovie.toString());
    }

}
