package cbs.cine_foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;

public interface MovieRepo extends JpaRepository<Movie, Long> {
    
    public List<Movie> findByUserProposed(final User user);


}
