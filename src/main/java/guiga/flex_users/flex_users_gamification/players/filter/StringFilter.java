package guiga.flex_users.flex_users_gamification.players.filter;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StringFilter extends AttributeFilter {
    private String operator;
    private String matchString;

    public StringFilter(String attrName, String filterString) {
        super(attrName);

        String[] filterComponents = filterString.split(":", 2);

        // dont accept empty filters
        if (filterComponents[1].isBlank() || filterComponents[1].isEmpty()) {
            throw new InvalidFilter("Cannot filter with empty values");
        }

        // figure out cmd
        switch (filterComponents[0]) {
            case "eq":
                this.operator = "=";
                this.matchString = "'" + filterComponents[1] + "'";
                break;
            case "ct":
                this.operator = "like";
                this.matchString = "'%" + filterComponents[1] + "%'";
                break;

            default:
                throw new InvalidFilter("Invalid string filter: '" + filterComponents[0] + "'");

        }
    }

    @Override
    public String toJsonbFilter() {
        return String.format("jsonb_extract_path_text(p.\"document\", '%s') %s %s",
                this.attrName,
                this.operator,
                this.matchString);
    }

}
