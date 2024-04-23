package guiga.flex_users.flex_users_gamification.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guiga.flex_users.flex_users_gamification.campaign.CampaignService;
import guiga.flex_users.flex_users_gamification.campaign.transfer.CampaignOut;
import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidPlayerDocument;
import guiga.flex_users.flex_users_gamification.players.filter.AttributeFilter;
import guiga.flex_users.flex_users_gamification.players.filter.NumberRangeFilter;
import guiga.flex_users.flex_users_gamification.players.filter.StringFilter;
import guiga.flex_users.flex_users_gamification.players.repo.PlayerCrudRepo;
import guiga.flex_users.flex_users_gamification.players.repo.PlayerCustomRepo;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerDeliverPointsIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerListIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerOut;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerParser;

@Service
public class PlayerService {
    private static HashMap<String, String> basePlayerSchema = new HashMap<>();

    static {
        basePlayerSchema.put("id", "Integer");
        basePlayerSchema.put("points", "Integer");
        basePlayerSchema.put("name", "String");
    }

    @Autowired
    private PlayerCrudRepo crud;

    @Autowired
    private PlayerCustomRepo customRepo;

    @Autowired
    private CampaignService campaignService;

    public PlayerOut savePlayer(PlayerIn playerIn) {
        CampaignOut campaign = campaignService.getCampaign(playerIn.getCampaignId());

        Map<String, String> documentSchema = campaign.getPlayerDocumentSchema();

        // to check if a player document is valid...
        for (Map.Entry<String, String> documentEntry : documentSchema.entrySet()) {
            String neededDocumentKey = documentEntry.getKey();

            // the documentEntry key must be present in the schema
            boolean keyPresent = playerIn.getDocument().keySet().contains(neededDocumentKey);
            if (!keyPresent) {
                throw new InvalidPlayerDocument("Attribute '" + neededDocumentKey
                        + "' not present in campaign schema!");
            }

            // the value must be the corresponding type
            String schemaType = documentEntry.getValue();
            Object playerDocumentValue = playerIn.getDocument().get(neededDocumentKey);

            if (!playerDocumentValue.getClass().getSimpleName().equals(schemaType)) {
                throw new InvalidPlayerDocument(
                        "Cannot parse '" + playerDocumentValue + "' to type '" + schemaType + "'");
            }
        }

        PlayerModel savedPlayerModel = crud.save(PlayerParser.from(playerIn));

        return PlayerParser.from(savedPlayerModel);
    }

    public List<PlayerOut> listPlayers(PlayerListIn playerListIn) {
        List<PlayerOut> output = new ArrayList<>();

        getPlayersFromFilterString(playerListIn.getCampaignId(), playerListIn.getFilterMap())
                .forEach(pm -> output.add(PlayerParser.from(pm)));

        return output;
    }

    private List<PlayerModel> getPlayersFromFilterString(Long campaignId, Map<String, String> filterMap) {
        CampaignOut campaign = campaignService.getCampaign(campaignId);
        Map<String, String> documentSchema = campaign.getPlayerDocumentSchema();

        List<AttributeFilter> attributeFilters = new ArrayList<AttributeFilter>();

        for (Map.Entry<String, String> filterEntry : filterMap.entrySet()) {
            // validate if its a base attribute or a document attribute
            if (basePlayerSchema.keySet().contains(filterEntry.getKey())) {
                attributeFilters.add(filterEntry2AttrFilter(filterEntry, basePlayerSchema, false));
            } else if (documentSchema.keySet().contains(filterEntry.getKey())) {
                attributeFilters.add(filterEntry2AttrFilter(filterEntry, documentSchema, true));
            } else {
                throw new InvalidFilter("Attribute '" + filterEntry.getKey()
                        + "' not present in campaign schema!");
            }
        }

        return customRepo.listPlayersByFilters(attributeFilters, campaignId);
    }

    private AttributeFilter filterEntry2AttrFilter(Map.Entry<String, String> filterEntry, Map<String, String> schema,
            boolean forDocument) {
        AttributeFilter af = null;
        String attrType = "";

        if (forDocument) {
            attrType = schema.get(filterEntry.getKey());
        } else {
            attrType = schema.get(filterEntry.getKey());
        }

        switch (attrType) {
            case "Integer":
            case "Double":
                af = new NumberRangeFilter(filterEntry.getKey(), forDocument, filterEntry.getValue());
                break;
            case "String":
                af = new StringFilter(filterEntry.getKey(), forDocument, filterEntry.getValue());
                break;

        }

        return af;

    }

    public List<PlayerOut> deliverPoints(PlayerDeliverPointsIn playerDeliverPointsIn) {
        List<PlayerModel> players = getPlayersFromFilterString(playerDeliverPointsIn.getCampaignId(),
                playerDeliverPointsIn.getPlayerFilter());

        for (PlayerModel pm : players) {
            pm.setPoints(pm.getPoints() + playerDeliverPointsIn.getAddPoints());

            crud.save(pm);
        }

        return players.stream().map(pm -> PlayerParser.from(pm)).toList();
    }

    public void removeKey(String deleteKey, Long campaignId) {
        crud.deleteDocumentKey(deleteKey, campaignId);
    }

    public void addKey(String newKey, String type, Long campaignId) {
        crud.addNewDocumentKey(newKey, getDefaultValue(type), campaignId);
    }

    private Object getDefaultValue(String type) {
        switch (type) {
            case "Integer":
                return 0;
            case "Double":
                return 0.0;
            default:
                return "EMPTY";
        }
    }
}
