package cbs.cine_foro.model;

import java.util.HashMap;
import java.util.Map;

import cbs.cine_foro.entity.Review;
import cbs.cine_foro.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewSimplifiedModel {
    
    private String userName;
    private Map<String, Float> moviesScores = new HashMap<>();

    // don't need the other things, right?

    /*public static ReviewSimplifiedModel userToSimplifiedModel(final Review review) {
        ReviewSimplifiedModel rw = new ReviewSimplifiedModel();
        rw.userName = review.getUser().getName();
        return null;
    }*/

}
