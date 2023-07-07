package cbs.cine_foro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Review;
import jakarta.annotation.PostConstruct;

@SpringBootTest
//@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class ReviewRepoTest {

    @Autowired
    private ReviewRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MovieRepo movieRepo;

    private List<Review> reviews;

    private List<User> users;

    private Movie movie;
    
    @PostConstruct
    void init() {        
        this.initUsers();
        this.initMovie();
        this.initReviews();
    }

     

    void initUsers() {
        users = List.of(
                new User("Pepote"),
                new User("Mariela"));

        try {
            userRepo.saveAll(users);
            users = userRepo.findAll();
        } catch (Exception e) {
            users = userRepo.findAll();
        }
    }

    void initMovie() {
        movie = movieRepo.findById(1L).orElse(null);

        if (movie != null)
            return;
            
        movie = Movie.builder()
                .originalTitle("Fingers crossed")
                .spanishTitle("La suerte del pringao")
                .build();
        movie.setUserProposed(users.get(0));
        
        try{
            movieRepo.save(movie);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("*********************************");
            movie = movieRepo.findById(1L).get();
        }
    }

    void initReviews() {
        if (reviews != null && reviews.size() > 0) {
            return;
        }

        reviews = new ArrayList<>();
        
        Review userOne = Review.builder()
                .movie(movie)
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();
            userOne.setUser(users.get(0));

        Review userTwo = Review.builder()
                .movie(movie)
                .score(6f)
                .bestMoment("El malo muere")
                .worstMoment("La música")
                .widow("Comer burritos durante el tiroteo")
                .build();
            userTwo.setUser(users.get(1));

        reviews.add(userOne);

        reviews.add(userTwo);

    }

    @Test
    @Order(1)
    void saveReview() {
        for (Review v : reviews) {
            Review result = repo.save(v);
            assertEquals(v.getReviewId(), result.getReviewId());
        }
    }
    

    @Test
    @Order(2)
    void saveReviewWithReviewUserIdWrong() {
        Review review = Review.builder()
                .user(User.builder().userId(69L).build())
                .movie(Movie.builder().movieId(1L).build())
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> repo.save(review));
        // repo.save(veredict); // exception
    }

    @Test
    @Order(3)
    void saveReviewWithReviewRepeatedIDS() {
        Review review = Review.builder()
                .movie(movie)
                .user(users.get(0))
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();
        //review.setUser(users.get(0));

        List<Movie> movies = movieRepo.findAll();

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> repo.save(review));
    }
    
    @Test
    @Order(4)
    void findAllReviews() {
        List<Review> ll = repo.findAll();
        assertEquals(2, ll.size());
        System.out.println("**********************");
        System.out.println(ll);
        System.out.println("**********************");
    }

    @Test
    @Order(5)
    void findAllByUserId() {
        List<Review> veredicts = repo.findAllByUserId(2L);
        assertEquals(veredicts.size(), 1);
    }

    @Test
    @Order(6)
    void findAllByMovieId() {
        List<Review> veredicts = repo.findAllByMovieId(1L);
        assertEquals(2, veredicts.size());
    }

    @Test
    @Order(7)
    void findAllByUserName() {
        List<Review> vers = repo.findAllByUserName("Pepote");
        System.out.println("**********************");
        System.out.println(vers);
        System.out.println("**********************");
        assertEquals(1, vers.size());
    }

    @Test
    @Order(8)
    // used at the end, easy to clean up
    void deleteAll(){
        repo.deleteAll();
        movieRepo.deleteAll();
        userRepo.deleteAll();
    }

    // delete veredict
    

    // update veredict

    // and don't know what else
}
