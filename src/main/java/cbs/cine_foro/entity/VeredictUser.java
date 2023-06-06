package cbs.cine_foro.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class VeredictUser {
    
    @OneToOne
    @JoinColumn(name="user_id")
    private User userId;
    private Float score;
    private String bestMoment;
    private String worstMoment;
    private String widow;
}
