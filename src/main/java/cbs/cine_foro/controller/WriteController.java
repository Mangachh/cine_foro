package cbs.cine_foro.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.aspectj.weaver.ast.Literal;
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
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.entity.VeredictUser;
import cbs.cine_foro.error.MovieNotExistsException;
import cbs.cine_foro.error.NationalityNotExistsException;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.error.VeredictMovieExistsException;
import cbs.cine_foro.error.VeredictNotExistsException;
import cbs.cine_foro.model.MovieModel;
import cbs.cine_foro.model.VeredictModel;
import cbs.cine_foro.service.IMovieService;
import cbs.cine_foro.service.INationService;
import cbs.cine_foro.service.IUserService;
import cbs.cine_foro.service.IVeredictService;
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
    private IVeredictService veredictService;

    //dummy data
    @PostConstruct
    public void dummyData() throws UserAlreadyExistsException, VeredictMovieExistsException {
        // create users
        User a = new User("Pepote");
        User b = new User("Mariela");
        User c = new User("Pichan");
        this.userService.saveUser(a);
        this.userService.saveUser(b);
        this.userService.saveUser(c);

        // movies
        Movie mA = Movie.builder()
                .originalTitle("仕事に行きたくない")
                .spanishTitle("Las palabrotas de pepote")
                .proposedDate(LocalDate.of(2021, 01, 07))
                .userProposed(a)
                .releaseYear("2015")
                .nationalities(List.of(
                        new Nationality("Japan")))
                .build();
        Movie mB = Movie.builder()
                .originalTitle("Party Party's")
                .spanishTitle("Fiesta sin fiesta")
                .proposedDate(LocalDate.of(1985, 12, 15))
                .userProposed(b)
                .releaseYear("1958")
                .nationalities(List.of(
                        new Nationality("USA"),
                        new Nationality("Canada")))
                .build();

        this.movieService.saveMovie(mA);
        this.movieService.saveMovie(mB);

        Veredict vA = Veredict.builder()
                .movie(mA)
                .userVeredict(
                        List.of(new VeredictUser(c, 8.5f, "Cuando pepote se cae", "La música", "LA escena final")))
                .build();

        Veredict vb = Veredict.builder()
                .movie(mA)
                .userVeredict(
                        List.of(new VeredictUser(b, 2.3f, "Las cortinas de zanahoria", "Eso de allí.", "Mascotas turbias")))
                .build();

        this.veredictService.saveVeredict(vb);
        this.veredictService.saveVeredict(vA);
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
        Set<Veredict> nn = movie.getVeredicts();
        movie.setMovieId(id);
        movie.getVeredicts().clear();
        movie.setVeredicts(nn);
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
    @PostMapping("veredict")
    public Veredict createVeredict(@RequestBody final VeredictModel veredict)
            throws MovieNotExistsException, UserNotExistsException, VeredictMovieExistsException {
        Movie m = this.movieService.getMovieById(veredict.getMovieId());
        User u = this.userService.getUserById(veredict.getUserId());
        Veredict v = this.veredictModelToVeredict(veredict);
        v.setMovie(m);
        return this.veredictService.saveVeredict(v);
    }

    @DeleteMapping("veredict/{id}")
    public void deleteVeredict(@PathVariable(name = "id") final Long id) {
        this.veredictService.deleteVeredictById(id);
    }

    @PutMapping("veredict/{id}")
    public Veredict updateVeredict(@PathVariable(name = "id") final Long id,
            @RequestBody final VeredictModel veredict) throws VeredictNotExistsException, MovieNotExistsException, UserNotExistsException, VeredictMovieExistsException {
        
        Veredict old = this.veredictService.getVeredictById(id);

        // do the checks 'cause it's fastest
        if (old.getMovie().getMovieId() != veredict.getMovieId()) {
            old.setMovie(this.movieService.getMovieById(veredict.getMovieId()));
        }

        if (old.getUserVeredict().getUser().getUserId() != veredict.getUserId()) {
            old.getUserVeredict().setUser(this.userService.getUserById(veredict.getUserId()));
        }

        // TODO: optimize?
        old.getUserVeredict().setBestMoment(veredict.getBestMoment());
        old.getUserVeredict().setWorstMoment(veredict.getWorstMoment());
        old.getUserVeredict().setWidow(veredict.getWidow());
        old.getUserVeredict().setScore(veredict.getScore());
        
        return this.veredictService.saveVeredict(old);        
    }

    private Veredict veredictModelToVeredict(final VeredictModel veredict) {
        VeredictUser verUser = VeredictUser.builder().score(veredict.getScore()).bestMoment(veredict.getBestMoment())
                .worstMoment(veredict.getWorstMoment()).widow(veredict.getWidow()).build();

        return Veredict.builder().userVeredict(verUser).build();

    }

    // TODO:
    /*
     * create some model classes -> Works fine and it's easy to work with.
     * Now:
     * - add a list of veredicts
     * - create web page
     * - security with the insert
     * 
     */
}
