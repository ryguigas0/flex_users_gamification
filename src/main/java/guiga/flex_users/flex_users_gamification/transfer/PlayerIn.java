package guiga.flex_users.flex_users_gamification.transfer;

import java.util.Map;

import guiga.flex_users.flex_users_gamification.exceptions.InvalidPlayerDocument;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PlayerIn {
    @NotBlank
    private String name;

    @Builder.Default
    private Integer points = 0;

    private Map<String, Object> document;

    public PlayerIn(String name, Integer points, Map<String, Object> document) {
        this.name = name;

        this.points = points;

        for (Object value : document.values()) {
            if (!(value instanceof String || value instanceof Integer || value instanceof Double)) {
                throw new InvalidPlayerDocument();
            }
        }

        this.document = document;
    }

}
