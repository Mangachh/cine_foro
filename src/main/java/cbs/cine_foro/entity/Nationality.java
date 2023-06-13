package cbs.cine_foro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(
    name = "nationalities",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_nationality",
        columnNames = "nationName"
    )
)
public class Nationality {
    
    @Id
    @SequenceGenerator(
        name = "nationality_generator",
        sequenceName = "nationality_generator",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "nationality_generator"
    )
    private Long nationalityId;
    private String nationName;

    public Nationality(final String nationality) {
        this.nationName = nationality;
    }
}
