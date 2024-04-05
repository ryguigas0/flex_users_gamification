package guiga.flex_users.flex_users_gamification;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class FallbackController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleExceptions(ResponseStatusException ex, WebRequest req) {
        Map<String, String> body = new HashMap<String, String>();
        body.put("error", ex.getReason());

        return new ResponseEntity<>(body, ex.getStatusCode());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleExceptions(NullPointerException ex, WebRequest req) {
        Map<String, String> body = new HashMap<String, String>();
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
