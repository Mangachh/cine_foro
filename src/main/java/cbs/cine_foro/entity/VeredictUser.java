package cbs.cine_foro.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
public class VeredictUser {
    
    @ManyToOne(cascade = {
                       CascadeType.MERGE,
                       CascadeType.REFRESH},
               fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", 
                foreignKey = @ForeignKey(name = "fk_veredict_user_id"),
                            referencedColumnName = "user_id")
    private User userId;
    private Float score;
    private String bestMoment;
    private String worstMoment;
    private String widow;
}
