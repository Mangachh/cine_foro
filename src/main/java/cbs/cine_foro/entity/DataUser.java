package cbs.cine_foro.entity;

import org.hibernate.id.factory.spi.GenerationTypeStrategy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataUserId;
    private String name;

    // just in case
    private String mail;
    private String password;
}
