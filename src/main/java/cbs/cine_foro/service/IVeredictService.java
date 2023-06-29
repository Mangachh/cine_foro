package cbs.cine_foro.service;

import java.util.List;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.error.VeredictMovieExistsException;
import cbs.cine_foro.error.VeredictNotExistsException;


public interface IVeredictService{
    
    Veredict saveVeredict(final Veredict veredict) throws VeredictMovieExistsException;

    Veredict getVeredictById(Long id) throws VeredictNotExistsException;

    void deleteVeredictById(final Long id);

    List<Veredict> getVeredictsByUser(final User user) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByUserId(final Long id) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByUserName(final String name) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByMovie(final Movie movie) throws VeredictNotExistsException;

    List<Veredict> getVeredictsByMovieId(final Long id) throws VeredictNotExistsException;

    List<Veredict> getAllVeredicts();

}
