package cbs.cine_foro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.MovieNotExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.service.IMovieService;
import cbs.cine_foro.service.IUserService;
import jakarta.websocket.server.PathParam;

/*
 * All the readings in the database will be in this controller.
 */
@RestController
public class ReadController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IMovieService movieService;

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

    // get between

    // get by nationality

    // get by average
}
