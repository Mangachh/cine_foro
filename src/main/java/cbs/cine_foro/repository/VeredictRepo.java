package cbs.cine_foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;

@Repository
public interface VeredictRepo extends JpaRepository<Veredict, Long> {
    
    @Query(
        value = "SELECT * FROM veredicts vs WHERE vs.user_id = ?1",
        nativeQuery = true)
    List<Veredict> findAllByUserId(Long userId);

    @Query(
        value = "SELECT * FROM veredicts vs " +
                "LEFT JOIN users us USING(user_id) WHERE us.name = ?1",
        nativeQuery = true
    )
    List<Veredict> findAllByUserName(final String name);

    @Query(
        value = "SELECT * FROM veredicts vs WHERE vs.movie_id = ?1",
        nativeQuery = true)
    List<Veredict> findAllByMovieId(Long movieId);

    // TODO: Tests!!!
    List<Veredict> findAllByMovie(final Movie movie);

    // ?
    List<Veredict> findAllByUserVeredictUser(final User user);
    
    

    
}
