package cbs.cine_foro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cbs.cine_foro.entity.Movie;

public interface MovieRepo extends JpaRepository<Movie, Long>{
    
}
