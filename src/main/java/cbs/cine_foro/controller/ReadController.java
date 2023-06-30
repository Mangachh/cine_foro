package cbs.cine_foro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.Nationality;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.error.MovieNotExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.error.VeredictNotExistsException;
import cbs.cine_foro.service.IMovieService;
import cbs.cine_foro.service.INationService;
import cbs.cine_foro.service.IUserService;
import cbs.cine_foro.service.IVeredictService;
import jakarta.websocket.server.PathParam;

/*
 * All the readings in the database will be in this controller.
 */
@RestController
@RequestMapping("/read/")
public class ReadController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IMovieService movieService;

    @Autowired
    private INationService nationService;

    @Autowired
    private IVeredictService veredictService;

    // read users
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(name = "id") final Long id) throws UserNotExistsException {
        return this.userService.getUserById(id);
    }

    @GetMapping("/user/")
    public User getUserByName(@RequestParam(name = "name") final String name) throws UserNotExistsException {
        return this.userService.getUserByName(name);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return this.userService.getAllUsers();
    }

    // movies
    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return this.movieService.getAllMovies();
    }

    @GetMapping("movie/{id}")
    public Movie getMovieById(@PathVariable(name = "id") final Long id) throws MovieNotExistsException {
        return this.movieService.getMovieById(id);
    }

    @GetMapping("/movie")
    public List<Movie> getMovieByUserId(@RequestParam(name = "userId", required = false) final Long userId,
                                        @RequestParam(name = "userName", required = false) final String name)
            throws UserNotExistsException {
        User user = null;
        if (userId != null) {
            user = this.userService.getUserById(userId);
        } else if (name != null) {
            user = this.userService.getUserByName(name);
        } else {
            // bad request

        }
        
        return this.movieService.getMoviesByUser(user);
    }

    // get by nationality
    @GetMapping("nations")
    public List<Nationality> getAllNations() {
        return this.nationService.getAllNationalities();
    }

    @GetMapping("veredicts")
    public List<Veredict> getAllVeredicts() {
        return this.veredictService.getAllVeredicts();
    }
    
    @GetMapping("veredict/movie/{id}")
    public List<Veredict> getAllVeredictsByMovieId(@PathVariable(name = "id") final Long id) throws VeredictNotExistsException{
        return this.veredictService.getVeredictsByMovieId(id);
    }


}
