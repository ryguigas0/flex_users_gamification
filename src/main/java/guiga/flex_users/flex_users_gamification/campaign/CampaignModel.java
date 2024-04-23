package guiga.flex_users.flex_users_gamification.campaign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Type;

import guiga.flex_users.flex_users_gamification.players.PlayerModel;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "campaigns")
@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
// Here so hibernate doesnt crash
@NoArgsConstructor
public class CampaignModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(name = "player_schema", columnDefinition = "jsonb")
    @Type(JsonType.class)
    private Map<String, String> playerSchema;

    // @OneToMany(orphanRemoval=true)
    // @JoinColumn(name="campaign_id")
    // @Builder.Default
    // private List<PlayerModel> players = new ArrayList<PlayerModel>();
}
