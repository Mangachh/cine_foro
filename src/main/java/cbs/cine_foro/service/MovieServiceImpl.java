package cbs.cine_foro.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.repository.MovieRepo;

@Service
public class MovieServiceImpl implements IMovieService {
    
    @Autowired 
    private MovieRepo repo;

    public Movie saveMovie(final Movie movie) {
        return repo.save(movie);
    }
    
    public Movie getMovieById(final Long id) {
        return repo.findById(id).orElse(null);
    }
    
    public List<Movie> getMoviesByUser(final User user) {
        //return repo.findAllByProposedBy(user);
        return null;
    }

    // average and sooo
    
    
    public void deleteMovie(final Movie movie) {
        
    }

}
