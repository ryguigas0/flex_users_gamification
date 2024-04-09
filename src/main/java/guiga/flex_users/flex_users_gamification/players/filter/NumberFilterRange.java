package guiga.flex_users.flex_users_gamification.players.filter;

import java.text.NumberFormat;

import org.springframework.beans.TypeMismatchException;

import guiga.flex_users.flex_users_gamification.players.exceptions.InvalidFilter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NumberFilterRange {
    private Number min = Double.MIN_VALUE;
    private Number max = Double.MAX_VALUE;

    // "inclusive range of 2 to 5" == 2,3,4,5
    // "exclusive range of 2 to 5" == 3,4
    private boolean inclusive;

    public NumberFilterRange(String filterString) {
        String[] filterComponents = filterString.split("(?<=[\\(\\[])|(?=[)\\]])|, ", 4);
        
        if (filterComponents.length == 1) {
            this.min = string2Number(filterComponents[0]);
            this.max = string2Number(filterComponents[0]);
            this.inclusive = true;
        }

    }

    private Number string2Number(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e1) {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e2) {
                throw new InvalidFilter("Invalid value '" + input + "' for number filtering!");
            }
        }
    }

}
