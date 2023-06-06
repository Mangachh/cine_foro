package cbs.cine_foro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Date;

import org.h2.result.ResultTarget;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import jakarta.annotation.PostConstruct;

//@DataJpaTest
@SpringBootTest
public class MovieRepoTest {
    @Autowired
    private MovieRepo repo;

    private static User user;

    @Autowired
    private UserRepo userRepo;

    @PostConstruct
    void setUp() {
        user = new User("Test User");
        userRepo.save(user);
    }
    

    @Test
    void saveMovieWithUser() {
        Movie movie = Movie.builder()
                        .proposedBy(user)
                        .originalTitle("Fingers, fingers, fingers")
                        .spanishTitle("Dedazos a porrazos")
                        .proposedDate(LocalDate.now())
                        .build();
                        
        Movie result = repo.save(movie);
        assertEquals(movie, result);
    }

    void getMovie() {

    }
    
    void removeMovie() {
        
    }
    
    // error!!!
    void saveMovieNoUser() {
        
    }

}
