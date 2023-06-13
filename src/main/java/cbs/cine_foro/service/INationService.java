package cbs.cine_foro.service;

import java.util.List;

import cbs.cine_foro.entity.Nationality;

public interface INationService {

    Nationality saveNationality(final Nationality nationality);
    
    List<Nationality> getAllNationalities();

    Nationality geNationalityByName(final String name);

    void removeNationality(final Nationality nationality);

    void removeNationalityByName(final String name);


}
