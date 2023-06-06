package cbs.cine_foro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cbs.cine_foro.entity.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    
    public Optional<User> findByName(String name);

    public void deleteByName(final String name);
}
