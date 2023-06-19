package cbs.cine_foro.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    // TODO: Change names on movieId and userId and so...
    private Long veredictId;
    @ManyToOne(cascade = {
                       CascadeType.REFRESH})
    @JoinColumn(name="movie_id", 
            foreignKey = @ForeignKey(name = "fk_veredict_movie_id"),
                         referencedColumnName = "MOVIE_ID")
    private Movie movieId;

    // esto puede ir a otra clase y hacer un embded? sip
    // ahora necesitamos otra tabla para esto, no?
    @Embedded
    private VeredictUser veredicts; 

    
    
}
