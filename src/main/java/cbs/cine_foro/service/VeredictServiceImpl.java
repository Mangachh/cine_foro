package cbs.cine_foro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        // TODO: exception if exist??? 
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
        List<Veredict> vers = repo.findAllByUserVeredictUser(user);
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
