package cbs.cine_foro.model;

import java.time.LocalDate;
import java.util.List;

import cbs.cine_foro.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieModel {
    
    private Long userProposedId;
    private String originalTitle;
    private String spanishTitle;
    private String releaseYear;
    private LocalDate proposedDate;
    private List<String> nationalities;

   

}
