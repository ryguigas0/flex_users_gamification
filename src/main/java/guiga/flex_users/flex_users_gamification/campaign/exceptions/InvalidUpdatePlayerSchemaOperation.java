package guiga.flex_users.flex_users_gamification.campaign.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUpdatePlayerSchemaOperation extends ResponseStatusException {
    public InvalidUpdatePlayerSchemaOperation(String reason) {
        super(HttpStatus.BAD_REQUEST, "Player Document Update Operation Error: " + reason);
    }
}