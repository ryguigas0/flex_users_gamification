package guiga.flex_users.flex_users_gamification.campaign.transfer;

import java.util.Map;

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
    // private List<PlayerOut> players;
}
