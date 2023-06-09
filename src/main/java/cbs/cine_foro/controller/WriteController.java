package cbs.cine_foro.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.Nationality;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Review;
import cbs.cine_foro.error.MovieNotExistsException;
import cbs.cine_foro.error.NationalityNotExistsException;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.error.ReviewMovieExistsException;
import cbs.cine_foro.error.ReviewNotExistsException;
import cbs.cine_foro.model.MovieModel;
import cbs.cine_foro.model.ReviewModel;
import cbs.cine_foro.service.IMovieService;
import cbs.cine_foro.service.INationService;
import cbs.cine_foro.service.IUserService;
import cbs.cine_foro.service.IReviewService;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.server.PathParam;

/**
 * All the writings in the database are here
 */
@RestController
@RequestMapping("write/")
public class WriteController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMovieService movieService;

    @Autowired
    private INationService nationService;

    @Autowired
    private IReviewService reviewService;

    //dummy data
    @PostConstruct
    public void dummyData() throws UserAlreadyExistsException, ReviewMovieExistsException {
        // create users
        User a = new User("Pepote");
        User b = new User("Mariela");
        User c = new User("Pichan");
        this.userService.saveUser(a);
        this.userService.saveUser(b);
        this.userService.saveUser(c);

        Nationality nationA = new Nationality("USA");
        Nationality nationB = new Nationality("Canada");
        Nationality nationC = new Nationality("Spain");
        //TODO: save list
        this.nationService.saveNationality(nationA);
        this.nationService.saveNationality(nationB);
        this.nationService.saveNationality(nationC);
        // movies
        Movie mA = Movie.builder()
                .originalTitle("仕事に行きたくない")
                .spanishTitle("Las palabrotas de pepote")
                .proposedDate(LocalDate.of(2021, 01, 07))
                .userProposed(a)
                .releaseYear("2015")
                .nationalities(List.of(
                        nationA))
                .build();
        Movie mB = Movie.builder()
                .originalTitle("Party Party's")
                .spanishTitle("Fiesta sin fiesta")
                .proposedDate(LocalDate.of(1985, 12, 15))
                .userProposed(b)
                .releaseYear("1958")
                .nationalities(List.of(
                        nationA,
                        nationB))
                .build();

        this.movieService.saveMovie(mA);
        this.movieService.saveMovie(mB);

        Review vA = Review.builder()
                .movie(mA)
                .user(a)
                .bestMoment("Cuando Pepote se cae")
                .worstMoment("Los pelillos de la nariz del prota")
                .widow("La ventana rota")
                .score(7.2f)                
                .build();

        Review vB = Review.builder()
                .movie(mA)
                .user(b)
                .bestMoment("Baldosas de Bart simpson")
                .worstMoment("Los caramelos de menta")
                .widow("El techo que se desploma cuando Pepota estornuda")
                .score(3.7f)                
                .build();
        
        Review vC = Review.builder()
                    .movie(mB)
                    .user(c)
                    .bestMoment("Hola hola")
                    .worstMoment("Adios adios")
                    .widow("Yuhuuuu")
                    .score(1.2f)
                .build();
        Review vD = Review.builder()
                    .movie(mB)
                    .user(b)
                    .bestMoment("lalala")
                    .worstMoment("cececeec")
                    .widow("aadasda")
                    .score(1.5f)
                .build();
        this.reviewService.saveReview(vB);
        this.reviewService.saveReview(vA);
        this.reviewService.saveReview(vC);
        this.reviewService.saveReview(vD);

        
    }
    
    // create/delete user
    @PostMapping("/user")
    public User createUser(@RequestBody User user) throws UserAlreadyExistsException {
        return this.userService.saveUser(user);
    }

    @PutMapping("/user/{id}")
    public User updateUserById(@PathVariable(name = "id") final Long id,
            @RequestParam(name = "newName") final String newName) throws UserNotExistsException {
        return this.userService.updateUserNameById(id, newName);
    }

    @PutMapping("/user/")
    public User updateUserByName(@RequestParam(name = "userName") final String name,
            @RequestParam(name = "newName") final String newName) throws UserNotExistsException {
        return this.userService.updateUserNameByName(name, newName);
    }

    @DeleteMapping("user/{id}")
    public void deleteUserById(@PathVariable(name = "id") final Long id) {
        this.userService.deleteUserById(id);
    }

    @DeleteMapping("/user/")
    public void deleteUserByName(@RequestParam(name = "name") final String name) {
        this.userService.deleteUserByName(name);
    }

    // create movie
    @PostMapping("movie/obsolete")
    public Movie createMovie(@RequestBody final Movie movie) {
        System.out.println(movie);
        // save nationalities first
        List<Nationality> newNations = new ArrayList<>();
        Nationality temp;

        for (Nationality n : movie.getNationalities()) {
            temp = this.nationService.geNationalityByNameNoException(n.getNationName());
            if (temp == null) {
                temp = this.nationService.saveNationality(n);
            }

            newNations.add(temp);
        }

        // List<Nationality> nList = movie.getNationalities();
        movie.getNationalities().clear();
        movie.setNationalities(newNations);
        Movie m = this.movieService.saveMovie(movie);

        return m;
    }

    @PostMapping("movie")
    public Movie createMovie(@RequestBody final MovieModel movie) throws UserNotExistsException {
        // get the user
        User u = this.userService.getUserById(movie.getUserProposedId());
        Movie m = this.movieModelToMovie(movie);
        m.setUserProposed(u);
        Nationality tempNation;
        List<Nationality> nationalities = new ArrayList<>();

        for (String nation : movie.getNationalities()) {
            tempNation = this.nationService.geNationalityByNameNoException(nation);

            if (tempNation == null) {
                tempNation = this.nationService.saveNationality(new Nationality(nation));
            }

            nationalities.add(tempNation);
        }

        m.setNationalities(nationalities);
        return this.movieService.saveMovie(m);
    }

    private Movie movieModelToMovie(final MovieModel model) {
        return Movie.builder().originalTitle(model.getOriginalTitle()).spanishTitle(model.getSpanishTitle())
                .releaseYear(model.getReleaseYear()).proposedDate(model.getProposedDate()).build();

    }

    // update movie
    @PutMapping("movie/{id}")
    public Movie updateMovie(@PathVariable(name = "id") final Long id,
            @RequestBody final Movie movie) {

        // mmmm, don't know how to do it
        Set<Review> nn = movie.getReviews();
        movie.setMovieId(id);
        movie.getReviews().clear();
        movie.setReviews(nn);
        this.movieService.saveMovie(movie);
        return this.movieService.saveMovie(movie);
    }

    // delete movie?
    @DeleteMapping("movie/{id}")
    public void deleteMovieById(@PathVariable(name = "id") final Long id) {
        this.movieService.deleteMovieById(id);
    }

    // create nationality
    @PostMapping("nation")
    public Nationality createNationality(@RequestBody final Nationality nationality) {
        return this.nationService.saveNationality(nationality);
    }

    @PutMapping("nation")
    public Nationality updateNationality(@RequestParam(name = "nation") final String nation,
            @RequestParam(name = "newNation") final String newNation) throws NationalityNotExistsException {

        Nationality nat = this.nationService.getNationalityByName(nation);
        nat.setNationName(newNation);
        return this.nationService.saveNationality(nat);
    }

    // create valid whatever
    @PostMapping("review")
    public Review createreview(@RequestBody final ReviewModel review)
            throws MovieNotExistsException, UserNotExistsException, ReviewMovieExistsException {
        Movie m = this.movieService.getMovieById(review.getMovieId());
        User u = this.userService.getUserById(review.getUserId());
        Review v = this.reviewModelToreview(review);
        v.setMovie(m);
        return this.reviewService.saveReview(v);
    }

    @DeleteMapping("review/{id}")
    public void deletereview(@PathVariable(name = "id") final Long id) {
        this.reviewService.deleteReviewById(id);
    }

    @PutMapping("review/{id}")
    public Review updatereview(@PathVariable(name = "id") final Long id,
            @RequestBody final ReviewModel review) throws ReviewNotExistsException, MovieNotExistsException, UserNotExistsException, ReviewMovieExistsException {
        
        Review old = this.reviewService.getReviewById(id);

        // do the checks 'cause it's fastest
        if (old.getMovie().getMovieId() != review.getMovieId()) {
            old.setMovie(this.movieService.getMovieById(review.getMovieId()));
        }

        if (old.getUser().getUserId() != review.getUserId()) {
            old.setUser(this.userService.getUserById(review.getUserId()));
        }

        // TODO: optimize?
        old.setBestMoment(review.getBestMoment());
        old.setWorstMoment(review.getWorstMoment());
        old.setWidow(review.getWidow());
        old.setScore(review.getScore());
        
        return this.reviewService.saveReview(old);        
    }

    private Review reviewModelToreview(final ReviewModel reviewModel) {
        Review review = Review.builder().score(reviewModel.getScore()).bestMoment(reviewModel.getBestMoment())
                .worstMoment(reviewModel.getWorstMoment()).widow(reviewModel.getWidow()).build();

        return review;

    }

    // TODO:
    /*
     * create some model classes -> Works fine and it's easy to work with.
     * Now:
     * - add a list of reviews
     * - create web page
     * - security with the insert
     * 
     */
}
