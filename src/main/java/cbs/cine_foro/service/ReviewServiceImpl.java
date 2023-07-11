package cbs.cine_foro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Review;
import cbs.cine_foro.error.ReviewMovieExistsException;
import cbs.cine_foro.error.ReviewNotExistsException;
import cbs.cine_foro.repository.ReviewRepo;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private ReviewRepo repo;

    @Override
    public Review saveReview(Review veredict) throws ReviewMovieExistsException {
        try{
            return repo.save(veredict);
        } catch (DataIntegrityViolationException e) {
            throw new ReviewMovieExistsException();
        }
        
    }

    @Override
    public Review getReviewById(Long id) throws ReviewNotExistsException {
        return repo.findById(id).orElseThrow(
            () -> new ReviewNotExistsException()
        );
    }

    @Override
    public List<Review> getAllReviewsByUser(User user) throws ReviewNotExistsException {
        List<Review> vers = repo.findAllByUser(user);
        if (vers == null || vers.size() == 0) {
            throw new ReviewNotExistsException();
        }

        return vers;
    }

    @Override
    public List<Review> getAllReviewsByUserName(String name) throws ReviewNotExistsException {
        List<Review> vers = repo.findAllByUserName(name);

        if (vers == null || vers.size() == 0) {
            throw new ReviewNotExistsException();
        }

        return vers;
    }

    @Override
    public List<Review> getAllReviewsByUserId(Long id) throws ReviewNotExistsException {
        List<Review> vers = repo.findAllByUserId(id);

        if (vers == null || vers.size() == 0) {
            throw new ReviewNotExistsException();
        }

        return vers;
    }

    @Override
    public List<Review> getAllReviewsByMovie(Movie movie) throws ReviewNotExistsException {
        List<Review> vers = repo.findAllByMovie(movie);

        if (vers == null || vers.size() == 0) {
            throw new ReviewNotExistsException();
        }

        return vers;
    }

    @Override
    public List<Review> getAllReviewsByMovieId(Long id) throws ReviewNotExistsException {
        List<Review> vers = repo.findAllByMovieId(id);

        if (vers == null || vers.size() == 0) {
            throw new ReviewNotExistsException();
        }

        return vers;
    }

    @Override
    public void deleteReviewById(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public List<Review> getAllReviews() {
        return this.repo.findAll();
    }

    @Override
    public List<Review> getAllUserBestScore() {
        return this.repo.findAllMaxScore();
    }

    @Override
    public List<Review> getAllUserWorstScore() {
        return this.repo.findAllWorstScore();
    }

}
