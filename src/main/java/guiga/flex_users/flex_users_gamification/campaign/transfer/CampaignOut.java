package guiga.flex_users.flex_users_gamification.campaign.transfer;

import java.util.List;
import java.util.Map;

import guiga.flex_users.flex_users_gamification.players.transfer.PlayerOut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CampaignOut {
    private Long id;
    private Map<String, String> playerDocumentSchema;
    private String name;
    private List<PlayerOut> players;
}
