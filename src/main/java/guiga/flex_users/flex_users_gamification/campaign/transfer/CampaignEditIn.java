package guiga.flex_users.flex_users_gamification.campaign.transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guiga.flex_users.flex_users_gamification.campaign.PlayerSchemaTypes;
import guiga.flex_users.flex_users_gamification.campaign.exceptions.InvalidPlayerSchemaType;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class CampaignEditIn {
    private Map<String, String> newAttributes = new HashMap<String, String>();
    private List<String> deleteAttributes = new ArrayList<String>();
    private String name;

    public CampaignEditIn(@Nullable String name, @Nullable Map<String, String> newAttributes,
            @Nullable List<String> deleteAttributes) {
        if (name != null) {
            this.name = name;
        }

        if (newAttributes != null) {
            for (Map.Entry<String, String> newAttribute : newAttributes.entrySet()) {
                if (!PlayerSchemaTypes.isAvaliableType(newAttribute.getValue())) {
                    throw new InvalidPlayerSchemaType("'" + newAttribute.getValue() + "' is not a valid type!");
                }
            }
            this.newAttributes = newAttributes;
        }

        if (deleteAttributes != null) {
            this.deleteAttributes = deleteAttributes;
        }
    }
}
