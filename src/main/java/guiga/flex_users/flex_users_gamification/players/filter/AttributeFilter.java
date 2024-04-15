package guiga.flex_users.flex_users_gamification.players.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AttributeFilter {
    protected String attrName;
    protected boolean forDocument = false;

    public String toSQL() {
        return null;
    }
}
