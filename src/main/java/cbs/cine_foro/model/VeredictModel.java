package cbs.cine_foro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeredictModel {
    private Long userId;
    private Long movieId;
    private Float score;
    private String bestMoment;
    private String worstMoment;
    private String widow;
}
