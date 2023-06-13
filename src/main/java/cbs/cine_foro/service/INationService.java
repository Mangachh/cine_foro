package cbs.cine_foro.service;

import java.util.List;

import cbs.cine_foro.entity.Nationality;
import cbs.cine_foro.error.NationalityNotExistsException;

public interface INationService {

    Nationality saveNationality(final Nationality nationality);
    
    List<Nationality> getAllNationalities();

    Nationality getNationalityByName(final String name) throws NationalityNotExistsException;

    void removeNationality(final Nationality nationality);

    void removeNationalityByName(final String name);


}
