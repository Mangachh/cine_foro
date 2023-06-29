package cbs.cine_foro.entity.security;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "data_users",
        uniqueConstraints = { @UniqueConstraint(name = "unique_data_user_name", columnNames = "user_name"),
                              @UniqueConstraint(name = "unique_data_user_email", columnNames = "email")})
public class DataUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataUserId;

    @Column(name = "user_name")
    private String userName;
    private String email;
    private String password;
}
