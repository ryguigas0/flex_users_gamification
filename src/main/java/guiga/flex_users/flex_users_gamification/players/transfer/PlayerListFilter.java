package guiga.flex_users.flex_users_gamification.players.transfer;

import java.util.Map;
import java.util.Map.Entry;
import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilterPattern;

public class PlayerListFilter {
    public PlayerListFilter(Map<String, String> filterMap) {
        System.out.println(filterMap);

        String numberRangeRegex = "(\\[|\\()?\\d+(.\\d+)?(, \\d+(.\\d+))?(]|\\))?";
        String stringFilterRegex = "(eq|ct):\"(\\w|\\d|\\s)+\"";

        for (Entry<String, String> filterEntry : filterMap.entrySet()) {
            if (!filterEntry.getValue().matches(numberRangeRegex)
                    && !filterEntry.getValue().matches(stringFilterRegex)) {
                throw new InvalidFilterPattern("'" + filterEntry.getKey() + "' has invalid pattern!");
            }
        }
    }
}
