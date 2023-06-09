package cbs.cine_foro.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.MovieNotExistsException;
import cbs.cine_foro.repository.MovieRepo;

@Service
public class MovieServiceImpl implements IMovieService {
    
    @Autowired 
    private MovieRepo repo;

    @Override
    public Movie saveMovie(final Movie movie) {
        return repo.save(movie);
    }
    
    @Override
    public Movie getMovieById(final Long id) throws MovieNotExistsException{
        return repo.findById(id).orElseThrow(() -> new MovieNotExistsException());
    }
    
    @Override
    public List<Movie> getMoviesByUser(final User user) {
        return repo.findAllByUserProposed(user);
    }

    // average and sooo
    
    @Override
    public void deleteMovie(final Movie movie) {
        repo.delete(movie);
    }

    @Override
    public void deleteMovieById(final Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Movie> getAllMovies() {
        return repo.findAll();
    }

    @Override
    public List<Movie> getMoviesByNationality(String nationName) throws MovieNotExistsException {
        // exception not movie found?
        List<Movie> movies = repo.findAllByNationalitiesNationName(nationName);
        if (movies == null || movies.size() == 0) {
            throw new MovieNotExistsException();
        }

        return movies;
    }

}
