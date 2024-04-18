package guiga.flex_users.flex_users_gamification.players.transfer;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PlayerIn {
    @NotNull
    private Long campaignId;

    @NotBlank
    private String name;

    @Builder.Default
    private Integer points = 0;

    private Map<String, Object> document;

    public PlayerIn(Long campaignId, String name, Integer points, Map<String, Object> document) {
        this.campaignId = campaignId;

        this.document = document;

        this.name = name;

        this.points = points;
    }

}
