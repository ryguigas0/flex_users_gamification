package guiga.flex_users.flex_users_gamification.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidPlayerDocument;
import guiga.flex_users.flex_users_gamification.players.filter.NumberFilterRange;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerListFilter;
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

    @Autowired
    private PlayerRepo repo;

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

        PlayerModel savedPlayerModel = repo.save(PlayerParser.from(playerIn));

        return PlayerParser.from(savedPlayerModel);
    }

    public List<PlayerOut> listPlayers(PlayerListFilter listFilter) {
        for (Map.Entry<String, String> filterEntry : listFilter.getFilterMap().entrySet()) {
            // check if filter entry key is in document schema
            if (!documentSchema.keySet().contains(filterEntry.getKey())) {
                throw new InvalidFilter("Attribute '" + filterEntry.getKey()
                        + "' not present in campaign schema!");
            }

            if (documentSchema.get(filterEntry.getKey()).equals("Integer")
                    || documentSchema.get(filterEntry.getKey()).equals("Double")) {
                System.out.println(filterEntry.getValue() + " --> " + new NumberFilterRange(filterEntry.getValue()));
            }

        }

        List<PlayerOut> output = new ArrayList<>();

        repo.findAll().forEach(pm -> output.add(PlayerParser.from(pm)));

        return output;
    }
}
