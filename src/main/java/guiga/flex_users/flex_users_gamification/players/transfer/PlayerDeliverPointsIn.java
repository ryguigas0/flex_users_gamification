package guiga.flex_users.flex_users_gamification.players.transfer;

import java.util.Map;

import lombok.Getter;

@Getter
public class PlayerDeliverPointsIn {
    private Long campaignId;

    private Map<String, String> playerFilter;

    private Integer addPoints;

    public PlayerDeliverPointsIn(Long campaignId, Map<String, String> filterMap, Integer addPoints) {
        this.campaignId = campaignId;

        this.addPoints = addPoints;

        PlayerListFilterIn playerListFilterIn = new PlayerListFilterIn(filterMap);

        this.playerFilter = playerListFilterIn.getFilterMap();
    }

}
