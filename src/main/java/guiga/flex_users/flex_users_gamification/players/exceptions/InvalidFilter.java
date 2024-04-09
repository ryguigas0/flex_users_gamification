package guiga.flex_users.flex_users_gamification.players.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidFilter extends ResponseStatusException {
    public InvalidFilter(String reason) {
        super(HttpStatus.BAD_REQUEST, "Invalid Filter: " + reason);
    }
}
