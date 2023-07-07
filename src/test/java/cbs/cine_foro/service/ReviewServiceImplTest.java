package cbs.cine_foro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Review;
import cbs.cine_foro.error.ReviewMovieExistsException;
import cbs.cine_foro.error.ReviewNotExistsException;
import cbs.cine_foro.repository.ReviewRepo;
import jakarta.annotation.PostConstruct;

@SpringBootTest
public class ReviewServiceImplTest {

    @Autowired
    private IReviewService service;

    @MockBean
    private ReviewRepo repo;

    // create 2-3 veredicts
    List<Review> veredicts;
    List<User> users;
    Movie movie;

    @PostConstruct
    void init() {
        // Users
        users = List.of(
                new User(1L, "Pepote"),
                new User(2L, "Mariela"),
                new User(3L, "Corazón"));

        movie = Movie.builder()
                .movieId(1L)
                .userProposed(users.get(0))
                .originalTitle("Pepote in pepoteland")
                .build();

        veredicts = List.of(
                Review.builder()
                        .movie(movie)
                        .reviewId(1L)
                        .user(users.get(0))
                        .score(7.0f)
                        .bestMoment("Mejor_A")
                        .worstMoment("Peor_A")
                        .widow("Viuda_A")
                        .build(),

                Review.builder()
                        .movie(movie)
                        .reviewId(2L)
                        .user(users.get(1))
                        .score(2.7f)
                        .bestMoment("Mejor_B")
                        .worstMoment("Peor_B")
                        .widow("Viuda_B")
                        .build(),

                Review.builder()
                        .movie(movie)
                        .reviewId(1L)
                        .user(users.get(2))
                        .score(8.5f)
                        .bestMoment("Mejor_C")
                        .worstMoment("Peor_C")
                        .widow("Viuda_C")
                        .build());
    }

    @Test
    void testGetReviewById() throws ReviewNotExistsException {
        final Long id = 1L;
        final Optional<Review> expected = this.veredicts.stream().filter(p -> p.getReviewId() == id).findFirst();
        Mockito.when(repo.findById(id))
                .thenReturn(expected);

        Review result = service.getReviewById(id);

        assertEquals(expected.get(), result);
    }

    @Test
    void testGetReviewByIdNotFound() throws ReviewNotExistsException {
        final Long id = 100L;
        Mockito.when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ReviewNotExistsException.class, () -> service.getReviewById(id));
    }

    @Test
    void testGetReviewsByMovie() throws ReviewNotExistsException {
        // one movie only, get all
        Mockito.when(repo.findAllByMovie(movie))
                .thenReturn(this.veredicts);

        List<Review> vers = this.service.getAllReviewsByMovie(movie);
        assertEquals(this.veredicts, vers);
    }

    @Test
    void testGetReviewsByMovieNotFound() throws ReviewNotExistsException {
        // one movie only, get all
        Mockito.when(repo.findAllByMovie(movie))
                .thenReturn(List.of());

        assertThrows(ReviewNotExistsException.class, () -> service.getAllReviewsByMovie(movie));
    }

    @Test
    void testGetReviewsByMovieId() throws ReviewNotExistsException {
        final Long id = 1L;
        // one movie only, get all
        Mockito.when(repo.findAllByMovieId(id))
                .thenReturn(this.veredicts);

        List<Review> vers = this.service.getAllReviewsByMovieId(id);
        assertEquals(this.veredicts, vers);
    }

    @Test
    void testGetReviewsByMovieIdNotFound() throws ReviewNotExistsException {
        final Long id = 1L;
        // one movie only, get all
        Mockito.when(repo.findAllByMovieId(id))
                .thenReturn(List.of());

        assertThrows(ReviewNotExistsException.class, () -> service.getAllReviewsByMovieId(id));
    }

    @Test
    void testGetReviewsByUser() throws ReviewNotExistsException {
        final User user = users.get(1);
        List<Review> vers = this.veredicts.stream()
                .filter(p -> p.getUser().getUserId() == user.getUserId()).toList();

        Mockito.when(repo.findAllByUser(user))
                .thenReturn(vers);

        assertEquals(vers, this.service.getAllReviewsByUser(user));
    }

    @Test
    void testGetReviewsByUserNotExist() {
        User u = new User();
        Mockito.when(repo.findAllByUser(u))
                .thenReturn(List.of());

        assertThrows(ReviewNotExistsException.class, () -> service.getAllReviewsByUser(u));
    }

    @Test
    void testGetReviewsByUserId() throws ReviewNotExistsException {
        final Long id = 2L;
        List<Review> vers = this.veredicts.stream().filter(p -> p.getUser().getUserId() == id)
                .toList();

        Mockito.when(repo.findAllByUserId(id))
                .thenReturn(vers);

        assertEquals(vers, this.service.getAllReviewsByUserId(id));
    }

    @Test
    void testGetReviewsByUserIdNotExist() {
        final Long id = 55L;

        Mockito.when(this.repo.findAllByUserId(id))
                .thenReturn(List.of());

        assertThrows(ReviewNotExistsException.class, () -> this.service.getAllReviewsByUserId(id));
    }

    @Test
    void testGetReviewsByUserName() throws ReviewNotExistsException {
        final String userName = "Pepote";

        List<Review> vers = this.veredicts.stream()
                .filter(p -> p.getUser().getName().equals(userName)).toList();

        Mockito.when(this.repo.findAllByUserName(userName))
                .thenReturn(vers);

        assertEquals(vers, this.service.getAllReviewsByUserName(userName));
    }

    @Test
    void testGetReviewsByUserNameNotExists() {
        final String userName = "lalalalal";
        Mockito.when(this.repo.findAllByUserName(userName))
                .thenReturn(List.of());

        assertThrows(ReviewNotExistsException.class, () -> this.service.getAllReviewsByUserName(userName));
    }

    @Test
    void testSaveReview() throws ReviewMovieExistsException {
        Review a = new Review(1L, new Movie(), users.get(0), 5.2f, "", "", "");
        Mockito.when(this.repo.save(a))
                .thenReturn(a);

        assertEquals(a, this.service.saveReview(a));

    }
}
