package guiga.flex_users.flex_users_gamification.campaign.transfer;

import guiga.flex_users.flex_users_gamification.campaign.CampaignModel;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerParser;

public class CampaignParser {
    public static CampaignModel from(CampaignIn in) {
        return CampaignModel
                .builder()
                .name(in.getName())
                .playerSchema(in.getPlayerDocumentSchema())
                .build();
    }

    public static CampaignOut from(CampaignModel model) {
        return CampaignOut
                .builder()
                .id(model.getId())
                .name(model.getName())
                .playerDocumentSchema(model.getPlayerSchema())
                .players(model.getPlayers().stream().map(pm -> PlayerParser.from(pm)).toList())
                .build();
    }
}
