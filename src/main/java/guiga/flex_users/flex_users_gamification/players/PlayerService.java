package guiga.flex_users.flex_users_gamification.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidPlayerDocument;
import guiga.flex_users.flex_users_gamification.players.filter.AttributeFilter;
import guiga.flex_users.flex_users_gamification.players.filter.NumberRangeFilter;
import guiga.flex_users.flex_users_gamification.players.filter.StringFilter;
import guiga.flex_users.flex_users_gamification.players.repo.PlayerCrudRepo;
import guiga.flex_users.flex_users_gamification.players.repo.PlayerCustomRepo;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerDeliverPointsIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerListFilterIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerOut;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerParser;

@Service
public class PlayerService {

    private static HashMap<String, String> documentSchema = new HashMap<>();

    static {
        documentSchema.put("age", "Integer");
        documentSchema.put("performance", "Double");
        documentSchema.put("cel-number", "String");
    }

    private static HashMap<String, String> playerSchema = new HashMap<>();

    static {
        playerSchema.put("id", "Integer");
        playerSchema.put("points", "Integer");
        playerSchema.put("name", "String");
    }

    @Autowired
    private PlayerCrudRepo crud;

    @Autowired
    private PlayerCustomRepo customRepo;

    public PlayerOut savePlayer(PlayerIn playerIn) {
        // to check if a player document is valid...
        for (Map.Entry<String, Object> documentEntry : playerIn.getDocument().entrySet()) {
            String documentEntryKey = documentEntry.getKey();

            // the documentEntry key must be present in the schema
            boolean keyPresent = documentSchema.keySet().contains(documentEntryKey);
            if (!keyPresent) {
                throw new InvalidPlayerDocument("Attribute '" + documentEntryKey
                        + "' not present in campaign schema!");
            }

            // the value must either null or the corresponding type
            String schemaType = documentSchema.get(documentEntryKey);
            Object documentEntryValue = documentEntry.getValue();

            if (documentEntryValue != null && !documentEntryValue.getClass().getSimpleName().equals(schemaType)) {
                throw new InvalidPlayerDocument(
                        "Cannot parse '" + documentEntryKey + "' to type '" + schemaType + "'");
            }
        }

        PlayerModel savedPlayerModel = crud.save(PlayerParser.from(playerIn));

        return PlayerParser.from(savedPlayerModel);
    }

    public List<PlayerOut> listPlayers(PlayerListFilterIn listFilter) {
        List<PlayerOut> output = new ArrayList<>();

        getPlayersFromFilterString(listFilter.getFilterMap()).forEach(pm -> output.add(PlayerParser.from(pm)));

        return output;
    }

    private List<PlayerModel> getPlayersFromFilterString(Map<String, String> filterMap) {
        List<AttributeFilter> attributeFilters = new ArrayList<AttributeFilter>();

        for (Map.Entry<String, String> filterEntry : filterMap.entrySet()) {
            // validate if its a player attribute or player document attribute
            if (playerSchema.keySet().contains(filterEntry.getKey())) {
                attributeFilters.add(filterEntry2AttrFilter(filterEntry, false));
            } else if (documentSchema.keySet().contains(filterEntry.getKey())) {
                attributeFilters.add(filterEntry2AttrFilter(filterEntry, true));
            } else {
                throw new InvalidFilter("Attribute '" + filterEntry.getKey()
                        + "' not present in campaign schema!");
            }
        }

        return customRepo.listPlayersByFilters(attributeFilters);
    }

    private AttributeFilter filterEntry2AttrFilter(Map.Entry<String, String> filterEntry, boolean forDocument) {
        AttributeFilter af = null;
        String attrType = "";

        if (forDocument) {
            attrType = documentSchema.get(filterEntry.getKey());
        } else {
            attrType = playerSchema.get(filterEntry.getKey());
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
        List<PlayerModel> players = getPlayersFromFilterString(playerDeliverPointsIn.getPlayerFilter());

        for (PlayerModel pm : players) {
            pm.setPoints(pm.getPoints() + playerDeliverPointsIn.getAddPoints());

            crud.save(pm);
        }

        return players.stream().map(pm -> PlayerParser.from(pm)).toList();
    }
}
