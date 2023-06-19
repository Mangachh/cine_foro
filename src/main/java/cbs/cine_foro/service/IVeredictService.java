package cbs.cine_foro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.error.VeredictNotExistsException;


public interface IVeredictService{
    
    Veredict saveVeredict(final Veredict veredict);

    Veredict getVeredictById(Long id) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByUser(final User user) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByUserId(final Long id) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByUserName(final String name) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByMovie(final Movie movie) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByMovieId(final Long id) throws VeredictNotExistsException;


}
