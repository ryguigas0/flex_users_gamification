package guiga.flex_users.flex_users_gamification.campaign.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPlayerSchemaType extends ResponseStatusException {
    public InvalidPlayerSchemaType(String reason) {
        super(HttpStatus.BAD_REQUEST, "Invalid Player Document: " + reason);
    }
}