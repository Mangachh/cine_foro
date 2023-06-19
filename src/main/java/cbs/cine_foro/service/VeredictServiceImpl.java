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

import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
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
    public Veredict getVeredictById(Long id) {
        return repo.findById(id).orElse(null); // TODO: exception
    }

    @Override
    public List<Veredict> getVeredictsByUser(User user) {
        return repo.findAllByUserId(user.getUserId()); // TODO: exception?
    }

    @Override
    public List<Veredict> getVeredictsByUserName(String name) {
        return repo.findAllByUserName(name); // TODO: exception
    }

    @Override
    public List<Veredict> getVeredictsByUserId(Long id) {
        return repo.findAllByUserId(id); // TODO: exception
    }

}
