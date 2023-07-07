package cbs.cine_foro.service;

import java.util.List;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Review;
import cbs.cine_foro.error.ReviewMovieExistsException;
import cbs.cine_foro.error.ReviewNotExistsException;


public interface IReviewService{
    
    Review saveReview(final Review veredict) throws ReviewMovieExistsException;

    Review getReviewById(Long id) throws ReviewNotExistsException;

    void deleteReviewById(final Long id);

    List<Review> getAllReviewsByUser(final User user) throws ReviewNotExistsException;

    List<Review> getAllReviewsByUserId(final Long id) throws ReviewNotExistsException;

    List<Review> getAllReviewsByUserName(final String name) throws ReviewNotExistsException;

    List<Review> getAllReviewsByMovie(final Movie movie) throws ReviewNotExistsException;

    List<Review> getAllReviewsByMovieId(final Long id) throws ReviewNotExistsException;

    List<Review> getAllReviews();

}
