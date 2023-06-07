package cbs.cine_foro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.h2.result.ResultTarget;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

//@DataJpaTest
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class MovieRepoTest {
    @Autowired
    private MovieRepo repo;

    @Transient
    private User user;

    @Autowired
    private UserRepo userRepo;

    
    private List<Movie> movies;

    @PostConstruct
    void setUp() {
        user = new User("Test User");
        userRepo.save(user);
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
    @Transient
    void findMoviesByUser() {        
        List<Movie> resultList = repo.findByUserProposed(user);
        System.out.println(movies);
        System.out.println(user);
        System.out.println(resultList);
        assertTrue(resultList.size() == 2);
    }

    //@Test
    void getMovieById() {
        for (Movie m : movies) {
            Optional<Movie> result = repo.findById(m.getMovieId());
            assertTrue(result.isPresent());
            assertEquals(m, result.get());
        }
    }

    void removeMovie() {

    }

    // error!!!
    void saveMovieNoUser() {

    }

}
