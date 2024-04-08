package guiga.flex_users.flex_users_gamification.players.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPlayerDocument extends ResponseStatusException {
    public InvalidPlayerDocument(String reason) {
        super(HttpStatus.BAD_GATEWAY, "Invalid Player Document: " + reason);
    }
}
