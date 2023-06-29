package cbs.cine_foro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    private Long veredictId;

    @ManyToOne(cascade = {
                       CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id", 
            foreignKey = @ForeignKey(name = "fk_veredict_movie_id"),
            referencedColumnName = "movie_id")
    @JsonBackReference // this way we won't have infinite recursion
    private Movie movie;

    // Ideally this should be an array 
    @Embedded
    private VeredictUser userVeredict;
    

    
    
}
