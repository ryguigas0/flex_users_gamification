package guiga.flex_users.flex_users_gamification.players.transfer;

import java.util.Map;

import lombok.Builder;

@Builder
public record PlayerOut(Long id, Integer points, String name, Map<String, Object> document) {
}
