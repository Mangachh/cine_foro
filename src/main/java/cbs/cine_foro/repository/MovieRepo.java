package cbs.cine_foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.Nationality;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;

public interface MovieRepo extends JpaRepository<Movie, Long> {
    
    public List<Movie> findAllByUserProposed(final User user);

    public List<Movie> findAllByNationalities(final Nationality nationality);

    public List<Movie> findAllByNationalitiesNationName(final String nationality);

    List<Veredict> findAllByOriginalTitleContainingIgnoreCase(final String contains);

    List<Veredict> findAllByspanishTitleContainingIgnoreCase(final String contains);

}
