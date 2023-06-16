package cbs.cine_foro.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.hibernate.type.ListType;

import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movies")
public class Movie {
    @Id
    @SequenceGenerator(
        name="movie_sequence",
        sequenceName = "movie_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "movie_sequence"
    )
    @Column(name = "movie_id")
    private Long movieId;
    private String originalTitle;
    private String spanishTitle;
    
    @ManyToOne(optional = false)
    @JoinColumn(
        name = "user_id",
        foreignKey = @ForeignKey(name = "fk_movie_user_id"),
            referencedColumnName = "user_id")

    private User userProposed;
    private LocalDate proposedDate;
    private Float average;
    private String releaseYear;
    // list of many-to-many

    @ManyToMany(
            cascade = {
                       CascadeType.MERGE,
                       CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinColumn(
        name = "nationality_id",
        referencedColumnName = "natioality_id",
        foreignKey = @ForeignKey(name = "fk_nationality_id")
    )
    private List<Nationality> nationalities;   
    //private Nationality nationalities;

}
