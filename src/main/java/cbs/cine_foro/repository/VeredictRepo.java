package cbs.cine_foro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cbs.cine_foro.entity.Veredict;

public interface VeredictRepo extends JpaRepository<Veredict, Long> {
    
    @Query(
        value = "SELECT * FROM veredicts vs WHERE vs.user_id = ?",
        nativeQuery = true)
    List<Veredict> findAllByUserId(Long userId);
}
