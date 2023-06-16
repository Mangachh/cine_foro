package cbs.cine_foro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.MovieNotExistsException;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.repository.MovieRepo;
import jakarta.annotation.PostConstruct;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class MovieServiceImplTest {

    @Autowired
    private IMovieService service;

    @MockBean
    private MovieRepo repo;

 
    
    private User user;

    private List<Movie> movies;

    @PostConstruct
    void setUp() throws UserNotExistsException {
        user = new User("Test user for MovieService");

        this.createMovies();
        
    }
    
    private void createMovies() {
        movies = List.of(
                Movie.builder()
                        .movieId(1L)
                        .spanishTitle("Tequeños y pequeños")
                        .originalTitle("Nothing about me")
                        .proposedDate(LocalDate.now())
                        .userProposed(user)
                        .build(),
                Movie.builder()
                        .movieId(2L)
                        .spanishTitle("Hola Caracola")
                        .originalTitle("Hola caracola")
                        .proposedDate(LocalDate.now())
                        .userProposed(user)
                        .build()
        );
        movies = new ArrayList<Movie>(movies);
    }

    @Test
    void testSaveMovie() {
        
        for (Movie m : movies) {
            Mockito.when(repo.save(m))
                    .thenReturn(m);
            Movie result = this.service.saveMovie(m);
            assertEquals(m, result);
        }
    }

    @Test
    void testGetAllMovies() {
        Mockito.when(repo.findAll())
                .thenReturn(this.movies);
        List<Movie> result = service.getAllMovies();

        assertEquals(this.movies, result);     
    }

    @Test
    void testGetMovieById() throws MovieNotExistsException {
        final Long id = 1L;
        Mockito.when(repo.findById(id))
                .thenReturn(this.movies.stream().filter(m -> m.getMovieId() == id).findFirst());

        Movie result = service.getMovieById(id);
        assertEquals(id, result.getMovieId());
        //assertEquals(newMovie.getOriginalTitle(), result.getOriginalTitle());

    }

    @Test
    void testGetMovieByNoExistingId() {
        final Long id = 9999999L;
        Mockito.when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(MovieNotExistsException.class, 
                    () -> service.getMovieById(id));
    }
    
    @Test
    void testDeleteMovieById() throws MovieNotExistsException {
       
        
    }

    @Test
    void testGetMoviesByUser() {
        Mockito.when(repo.findAllByUserProposed(user))
                .thenReturn(movies.stream().filter(m -> m.getUserProposed() == user).toList());

        List<Movie> moviesByUser = service.getMoviesByUser(user);
        assertEquals(movies.size(), moviesByUser.size());
        
    }    

    //@Test
    void testDeleteMovieByMovie() throws MovieNotExistsException {
        //check if exist
        Movie m = service.getMovieById(1L);
        assertNotNull(m);

        service.deleteMovieById(1L);
        assertThrows(MovieNotExistsException.class,
                () -> service.getMovieById(999999999L));
    }
    
    @Test
    void testGetMoviesByNationalityNoExist() {
        Mockito.when(repo.findAllByNationalitiesNationName("PepoteNation"))
                .thenReturn(List.of());
                
        assertThrows(MovieNotExistsException.class, 
                () -> service.getMoviesByNationality("PepoteNation"));
        
    }

   

    

    
}
