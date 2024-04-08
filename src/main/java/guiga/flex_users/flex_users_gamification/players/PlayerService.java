package guiga.flex_users.flex_users_gamification.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidPlayerDocument;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerListFilter;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerOut;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerParser;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepo repo;

    public PlayerOut savePlayer(PlayerIn playerIn) {
        HashMap<String, String> documentSchema = new HashMap<>();

        documentSchema.put("age", "Integer");
        documentSchema.put("performance", "Double");
        documentSchema.put("cel-number", "String");

        // to check if a player document is valid...
        for (Map.Entry<String, Object> documentEntry : playerIn.getDocument().entrySet()) {
            String documentEntryKey = documentEntry.getKey();

            // the documentEntry key must be present in the schema
            boolean keyPresent = documentSchema.keySet().stream()
                    .anyMatch(schemaKey -> documentEntryKey.equals(schemaKey));
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
        System.out.println(listFilter);

        List<PlayerOut> output = new ArrayList<>();

        repo.findAll().forEach(pm -> output.add(PlayerParser.from(pm)));

        return output;
    }
}
