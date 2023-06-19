package cbs.cine_foro.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.error.VeredictNotExistsException;
import cbs.cine_foro.repository.VeredictRepo;

@Service
public class VeredictServiceImpl implements IVeredictService {

    @Autowired
    private VeredictRepo repo;

    @Override
    public Veredict saveVeredict(Veredict veredict) {
        return repo.save(veredict);
    }

    @Override
    public Veredict getVeredictById(Long id) throws VeredictNotExistsException {
        return repo.findById(id).orElseThrow(
            () -> new VeredictNotExistsException()
        );
    }

    @Override
    public List<Veredict> getVeredictsByUser(User user) throws VeredictNotExistsException {
        List<Veredict> vers = repo.findAllByUser(user);
        if (vers == null || vers.size() == 0) {
            throw new VeredictNotExistsException();
        }

        return vers;
    }

    @Override
    public List<Veredict> getVeredictsByUserName(String name) throws VeredictNotExistsException {
        List<Veredict> vers = repo.findAllByUserName(name);

        if (vers == null || vers.size() == 0) {
            throw new VeredictNotExistsException();
        }

        return vers;
    }

    @Override
    public List<Veredict> getVeredictsByUserId(Long id) throws VeredictNotExistsException {
        List<Veredict> vers = repo.findAllByUserId(id);

        if (vers == null || vers.size() == 0) {
            throw new VeredictNotExistsException();
        }

        return vers;
    }

    @Override
    public List<Veredict> getVeredictsByMovie(Movie movie) throws VeredictNotExistsException {
        List<Veredict> vers = repo.findAllByMovie(movie);

        if (vers == null || vers.size() == 0) {
            throw new VeredictNotExistsException();
        }

        return vers;
    }

    @Override
    public List<Veredict> getVeredictsByMovieId(Long id) throws VeredictNotExistsException {
        List<Veredict> vers = repo.findAllByMovieId(id);

        if (vers == null || vers.size() == 0) {
            throw new VeredictNotExistsException();
        }

        return vers;
    }

}
