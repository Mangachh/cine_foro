package cbs.cine_foro.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cbs.cine_foro.entity.security.DataUser;

public interface DataUserRepo extends JpaRepository<DataUser, Long>{
    Optional<DataUser> findByUserName(final String userName);

    Optional<DataUser> findByEmail(final String mail);
}
