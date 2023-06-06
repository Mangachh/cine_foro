package cbs.cine_foro.entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.Cascade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long movieId;
    private String originalTitle;
    private String spanishTitle;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User proposedBy;
    private LocalDate proposedDate;
    private Float average;
}
