package guiga.flex_users.flex_users_gamification.players.filter;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StringFilter {
    private String attrName;
    private boolean equals = false;
    private boolean contains = false;
    private String matchString;

    public StringFilter(String attrName, String filterString) {
        this.attrName = attrName;
        String[] filterComponents = filterString.split(":", 2);

        // dont accept empty filters
        if (filterComponents[1].isBlank() || filterComponents[1].isEmpty()) {
            throw new InvalidFilter("Cannot filter with empty values");
        }

        // figure out cmd
        switch (filterComponents[0]) {
            case "eq":
                this.equals = true;
                break;
            case "ct":
                this.contains = true;

            default:
                throw new InvalidFilter("Invalid string filter: '" + filterComponents[0] + "'");

        }
    }
}
