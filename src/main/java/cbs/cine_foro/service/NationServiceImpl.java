package cbs.cine_foro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cbs.cine_foro.entity.Nationality;
import cbs.cine_foro.repository.NationalityRepo;

@Service
public class NationServiceImpl implements INationService {

    @Autowired
    private NationalityRepo repo;

    @Override
    public Nationality saveNationality(Nationality nationality) {
        return repo.save(nationality);
    }

    @Override
    public List<Nationality> getAllNationalities() {
        return repo.findAll();
    }

    @Override
    public Nationality geNationalityByName(String name) {
        return repo.findByNationName(name); //exception?s
    }

    @Override
    public void removeNationality(Nationality nationality) {
        repo.delete(nationality); // exception?
    }

    @Override
    public void removeNationalityByName(String name) {
        repo.deleteByNationName(name); // exception?
    }
    
}
