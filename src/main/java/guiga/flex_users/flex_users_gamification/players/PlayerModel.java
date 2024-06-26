package guiga.flex_users.flex_users_gamification.players;

import java.util.Map;

import org.hibernate.annotations.Type;

import guiga.flex_users.flex_users_gamification.campaign.CampaignModel;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "players")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
// Here so hibernate doesnt crash
@NoArgsConstructor
public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    @Column
    private Integer points = 0;

    @Column
    private String name;

    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private Map<String, Object> document;

    @Column(name = "campaign_id")
    private Long campaignId;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false, insertable = false, updatable = false)
    private CampaignModel campaign;

}
