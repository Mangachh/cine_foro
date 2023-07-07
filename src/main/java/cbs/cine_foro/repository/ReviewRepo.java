package cbs.cine_foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    
    @Query(
        value = "SELECT * FROM reviews rs WHERE rs.user_id = ?1",
        nativeQuery = true)
    List<Review> findAllByUserId(Long userId);

    @Query(
        value = "SELECT * FROM reviews rs " +
                "LEFT JOIN users us USING(user_id) WHERE us.name = ?1",
        nativeQuery = true
    )
    List<Review> findAllByUserName(final String name);

    @Query(
        value = "SELECT * FROM reviews rs WHERE rs.movie_id = ?1",
        nativeQuery = true)
    List<Review> findAllByMovieId(Long movieId);

    // TODO: Tests!!!
    List<Review> findAllByMovie(final Movie movie);

    // ?
    List<Review> findAllByUser(final User user);
    
    

    
}
