package guiga.flex_users.flex_users_gamification.players.filter;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NumberRangeFilter extends AttributeFilter {
    private Number min = Double.MIN_VALUE;
    private Number max = Double.MAX_VALUE;

    // "inclusive range of 2 to 5" == 2,3,4,5
    // "exclusive range of 2 to 5" == 3,4
    private boolean inclusiveLeft = false;
    private boolean inclusiveRight = false;

    public NumberRangeFilter(String attrName, String filterString) {
        super(attrName);

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
        this.inclusiveLeft = true;
        this.inclusiveRight = true;
        this.min = string2Number(filterString);
        this.max = string2Number(filterString);
    }

    @Override
    public String toJsonbFilter() {
        String minOperand = this.inclusiveLeft ? ">=" : ">";
        String maxOperand = this.inclusiveRight ? "<=" : "<";

        String minFilter = String.format("jsonb_extract_path(p.\"document\", '%s')\\:\\:numeric %s %s",
                this.attrName,
                minOperand,
                this.getMin().toString());

        String maxFilter = String.format("jsonb_extract_path(p.\"document\", '%s')\\:\\:numeric %s %s",
                this.attrName,
                maxOperand,
                this.getMax().toString());

        return minFilter + " and " + maxFilter;
    }

    private void setMax(String filterComponent) {
        int lastIndex = filterComponent.length() - 1;
        switch (filterComponent.charAt(lastIndex)) {
            case ']':
                this.inclusiveRight = true;
                break;
            case ')':
                this.inclusiveRight = false;
                break;

            default:
                throw new InvalidFilter("Invalid expression for filter");
        }

        this.max = string2Number(filterComponent.substring(0, lastIndex));
    }

    private void setMin(String filterComponent) {
        switch (filterComponent.charAt(0)) {
            case '[':
                this.inclusiveLeft = true;
                break;
            case '(':
                this.inclusiveLeft = false;
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
