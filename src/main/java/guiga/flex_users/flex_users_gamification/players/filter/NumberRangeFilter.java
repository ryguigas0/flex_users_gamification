package guiga.flex_users.flex_users_gamification.players.filter;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NumberRangeFilter {
    private String attrName;
    private Number min = Double.MIN_VALUE;
    private Number max = Double.MAX_VALUE;

    // "inclusive range of 2 to 5" == 2,3,4,5
    // "exclusive range of 2 to 5" == 3,4
    private boolean inclusive_left = false;
    private boolean inclusive_right = false;

    public NumberRangeFilter(String attrName, String filterString) {
        this.attrName = attrName;
        String[] filterComponents = filterString.split(",");

        // has min and max
        if (filterComponents.length == 2) {
            setMin(filterComponents[0]);
            setMax(filterComponents[1]);
            return;
        }

        // has min or max
        if (filterComponents.length == 1) {
            try {
                setMin(filterComponents[0]);
            } catch (Exception e) {
                setMax(filterComponents[0]);
            }
            return;
        }

        // equals
        this.inclusive_left = true;
        this.inclusive_right = true;
        this.min = string2Number(filterString);
        this.max = string2Number(filterString);
    }

    private void setMax(String filterComponent) {
        int lastIndex = filterComponent.length() - 1;
        switch (filterComponent.charAt(lastIndex)) {
            case ']':
                this.inclusive_right = true;
                break;
            case ')':
                this.inclusive_right = false;
                break;

            default:
                throw new InvalidFilter("Invalid expression for filter");
        }

        this.max = string2Number(filterComponent.substring(0, lastIndex));
    }

    private void setMin(String filterComponent) {
        switch (filterComponent.charAt(0)) {
            case '[':
                this.inclusive_left = true;
                break;
            case '(':
                this.inclusive_left = false;
                break;

            default:
                throw new InvalidFilter("Invalid expression for filter");
        }

        this.min = string2Number(filterComponent.substring(1));
    }

    private Number string2Number(String input) {
        String trimmed = input.trim();
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e1) {
            try {
                return Double.parseDouble(trimmed);
            } catch (NumberFormatException e2) {
                throw new InvalidFilter("Invalid value '" + trimmed + "' for number filtering!");
            }
        }
    }

}
