package guiga.flex_users.flex_users_gamification.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPlayerDocument extends ResponseStatusException {
    public InvalidPlayerDocument() {
        super(HttpStatus.BAD_GATEWAY, "Invalid player document! Only integers, floats or strings");
    }
}
