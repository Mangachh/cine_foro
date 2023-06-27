package cbs.cine_foro.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.Nationality;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.error.NationalityNotExistsException;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.service.IMovieService;
import cbs.cine_foro.service.INationService;
import cbs.cine_foro.service.IUserService;
import jakarta.websocket.server.PathParam;

/**
 * All the writings in the database are here
 */
@RestController
public class WriteController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMovieService movieService;

    @Autowired
    private INationService nationService;

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
    @PostMapping("movie")
    public Movie createMovie(@RequestBody final Movie movie) {
        System.out.println(movie.getOriginalTitle());
        return this.movieService.saveMovie(movie);
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
}
