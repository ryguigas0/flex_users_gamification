package guiga.flex_users.flex_users_gamification.campaign.transfer;

import java.util.Map;

import guiga.flex_users.flex_users_gamification.campaign.PlyaerSchemaTypes;
import guiga.flex_users.flex_users_gamification.campaign.exceptions.InvalidPlayerSchemaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CampaignIn {
    @NotEmpty
    private Map<String, String> playerDocumentSchema;
    @NotBlank
    private String name;

    public CampaignIn(@NotEmpty Map<String, String> playerDocumentSchema, @NotBlank String name) {
        this.name = name;

        for (Map.Entry<String, String> schemaEntry : playerDocumentSchema.entrySet()) {
            if (!PlyaerSchemaTypes.isAvaliableType(schemaEntry.getValue())) {
                throw new InvalidPlayerSchemaType("'" + schemaEntry.getValue() + "' is not a valid type!");
            }
        }

        this.playerDocumentSchema = playerDocumentSchema;
    }
}
