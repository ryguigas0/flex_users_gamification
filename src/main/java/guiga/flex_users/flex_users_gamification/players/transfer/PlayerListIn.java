package guiga.flex_users.flex_users_gamification.players.transfer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerListIn {
    @NotNull
    private Long campaignId;

    @Nullable
    private Map<String, String> filterMap;

    public PlayerListIn(Long campaignId, Map<String, String> filterMap) {
        this.campaignId = campaignId;

        String numberRangeRegex = "((\\[|\\()?)(\\d)+(.(\\d)+)?(, (\\d)+(.(\\d)+)?)?((]|\\))?)";
        String stringFilterRegex = "(eq|ct):(\\w|\\d|\\s|\\W)+";

        if (filterMap == null) {
            filterMap = new HashMap<String, String>();
        }

        for (Entry<String, String> filterEntry : filterMap.entrySet()) {
            if (!filterEntry.getValue().matches(numberRangeRegex)
                    && !filterEntry.getValue().matches(stringFilterRegex)) {
                throw new InvalidFilter("'" + filterEntry.getKey() + "' has invalid pattern!");
            }
        }

        this.filterMap = filterMap;
    }

}
