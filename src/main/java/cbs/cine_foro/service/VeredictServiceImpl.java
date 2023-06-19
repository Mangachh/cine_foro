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

import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.repository.VeredictRepo;

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
        return null;
    }

    @Override
    public List<Veredict> getVeredictsByUserName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVeredictsByUserName'");
    }

    @Override
    public List<Veredict> getVeredictsByUserId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVeredictsByUserId'");
    }

}
