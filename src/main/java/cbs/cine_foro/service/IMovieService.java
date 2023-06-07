package cbs.cine_foro.service;

import java.util.List;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;

public interface IMovieService {    
    public Movie saveMovie(final Movie movie);
    
    public Movie getMovieById(final Long id);
    
    public List<Movie> getMoviesByUser(final User user);    
    
    public void deleteMovie(final Movie movie);

}
