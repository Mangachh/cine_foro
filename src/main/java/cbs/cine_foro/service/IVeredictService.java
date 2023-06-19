package cbs.cine_foro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;


public interface IVeredictService{
    
    Veredict saveVeredict(final Veredict veredict);

    Veredict getVeredictById(Long id);

    List<Veredict> getVeredictsByUser(final User user);

    List<Veredict> getVeredictsByUserId(final Long id);

    List<Veredict> getVeredictsByUserName(final String name);


}
