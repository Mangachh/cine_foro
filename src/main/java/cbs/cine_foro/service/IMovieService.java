package cbs.cine_foro.service;

import java.util.List;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.MovieNotExistsException;

public interface IMovieService {
    
    public Movie saveMovie(final Movie movie);
    
    public Movie getMovieById(final Long id) throws MovieNotExistsException;

    public List<Movie> getAllMovies();
    
    public List<Movie> getMoviesByUser(final User user);    
    
    public void deleteMovie(final Movie movie);

    public void deleteMovieById(final Long id);

    public List<Movie> getMoviesByNationality(final String nation) throws MovieNotExistsException;

}
