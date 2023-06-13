package cbs.cine_foro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cbs.cine_foro.entity.Nationality;
import jakarta.transaction.Transactional;

public interface NationalityRepo extends JpaRepository<Nationality, Long> {
    
    public Nationality findByNationName(final String name);

    @Transactional
    public void deleteByNationName(final String name);

}
