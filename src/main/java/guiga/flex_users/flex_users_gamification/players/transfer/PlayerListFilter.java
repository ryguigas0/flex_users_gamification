package guiga.flex_users.flex_users_gamification.players.transfer;

import java.util.Map;
import java.util.Map.Entry;
import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import lombok.Getter;

public class PlayerListFilter {
    @Getter
    private Map<String, String> filterMap;

    public PlayerListFilter(Map<String, String> filterMap) {
        String numberRangeRegex = "((\\[|\\()?)(\\d)+(.(\\d)+)?(, (\\d)+(.(\\d)+)?)?((]|\\))?)";
        String stringFilterRegex = "(eq|ct):(\\w|\\d|\\s|\\W)+";

        for (Entry<String, String> filterEntry : filterMap.entrySet()) {
            if (!filterEntry.getValue().matches(numberRangeRegex)
                    && !filterEntry.getValue().matches(stringFilterRegex)) {
                throw new InvalidFilter("'" + filterEntry.getKey() + "' has invalid pattern!");
            }
        }

        this.filterMap = filterMap;
    }
}
