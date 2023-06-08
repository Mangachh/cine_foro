package cbs.cine_foro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import jakarta.annotation.PostConstruct;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class MovieServiceImplTest {

    @Autowired
    private IMovieService service;

    @Autowired
    private IUserService userService;
    
    private User user;

    private List<Movie> movies;
    private static Long idToDelete;

    @PostConstruct
    void setUp() throws UserNotExistsException {
        user = new User("Test user for MovieService");

        try {
            user = userService.saveUser(user);
        } catch (UserAlreadyExistsException e) {
            user = userService.getUserByName(user.getName());
            System.out.println("---------\n" + e.getMessage() + "\n" + user.toString() + "\n----------");
        }

        if (movies == null || movies.size() == 0) {
            this.createMovies();
        }
        
    }
    
    private void createMovies() {
        movies = List.of(
                    Movie.builder()
                        .spanishTitle("Tequeños y pequeños")
                        .originalTitle("Nothing about me")
                        .proposedDate(LocalDate.now())
                        .build(),
                    Movie.builder()
                        .spanishTitle("Hola Caracola")
                        .originalTitle("Hola caracola")
                        .proposedDate(LocalDate.now())
                        .build()
        );
        movies = new ArrayList<Movie>(movies);
    }

    @Test
    @Order(1)
    void testSaveMovie() {
        // first put the user
        movies.stream().forEach(m -> m.setUserProposed(user));
        for (Movie m : movies) {
            Movie result = this.service.saveMovie(m);
            assertEquals(m, result);
        }
    }

    @Test
    @Order(2)
    void testGetAllMovies() {
        List<Movie> result = service.getAllMovies();

        // we'll check if the titles are the same becuase
        // the list doesn't update
        for (Movie m : result) {
            boolean found = movies.stream().anyMatch(mm -> m.getOriginalTitle().equals(mm.getOriginalTitle()));
            assertTrue(found);
        }
    }

    @Test
    @Order(3)
    void testGetMovieById() {
        // save new movie, easier
        Movie newMovie = Movie.builder()
                .originalTitle("Movie to get By Id")
                .spanishTitle("No one")
                .build();
        newMovie.setUserProposed(user);

        newMovie = service.saveMovie(newMovie);
        Movie result = service.getMovieById(newMovie.getMovieId());
        assertEquals(newMovie, result);
        this.idToDelete = result.getMovieId();
    }
    
    @Test
    @Order(4)
    void testDeleteMovieById() {
        service.deleteMovieById(idToDelete);
        Movie m = service.getMovieById(idToDelete);
        assertNull(m); // EXCEPTION!!!
    }

    @Test
    @Order(5)
    void testGetMoviesByUser() {
        List<Movie> moviesByUser = service.getMoviesByUser(user);
        assertEquals(movies.size(), moviesByUser.size());
        for (Movie m : moviesByUser) {
            boolean result = movies.stream().anyMatch(mm -> mm.getOriginalTitle().equals(m.getOriginalTitle()));
            assertTrue(result);
        }
    }

    

    @Test
    void testDeleteMovieByMovie() {
        //
        service.deleteMovieById(1L);
        Movie m = service.getMovieById(1L);
        assertNull(m); // EXCEPTION!!
    }

   

    

    
}
