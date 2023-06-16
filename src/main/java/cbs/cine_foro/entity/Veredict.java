package cbs.cine_foro.entity;

import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "veredicts",
        uniqueConstraints = @UniqueConstraint(
        name = "unique_movie_user",
        columnNames = {"movie_id", "user_id"}
    )
)
public class Veredict {
    
    @Id
    @SequenceGenerator(
        name="veredict_sequence",
        sequenceName = "veredict_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "veredict_sequence"
    )

    private Long veredictId;
    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movieId;

    // esto puede ir a otra clase y hacer un embded? sip
    // ahora necesitamos otra tabla para esto, no?
    @Embedded
    private VeredictUser veredicts; 

    
    
}
