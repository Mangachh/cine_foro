package cbs.cine_foro.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @Transient
    private float average;
    private String releaseYear;
    // list of many-to-many

    @ManyToMany(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "nationality_id",
        referencedColumnName = "natioality_id",
        foreignKey = @ForeignKey(name = "fk_nationality_id")
    )
    private List<Nationality> nationalities = new ArrayList<>();
    
    @OneToMany(
            mappedBy = "movie", 
            cascade = { CascadeType.REFRESH,
                        CascadeType.REMOVE },
            fetch = FetchType.EAGER,
        orphanRemoval = true)
    private Set<Review> reviews = new HashSet<Review>();
    
    public float getAverage() {
        if (reviews == null || reviews.size() == 0)
            return -1f;

        return (float) reviews.stream().mapToDouble(v -> v.getScore()).sum() / reviews.size();
    }
    
    public void setReviews(final Set<Review> veredicts) {
        this.reviews.addAll(veredicts);
    }

    public void setNationalities(final List<Nationality> nationalities) {
        if(this.nationalities == null){
            this.nationalities = new ArrayList<>();
        }
        
        this.nationalities.addAll(nationalities);
    }



}
