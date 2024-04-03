package guiga.flex_users.flex_users_gamification;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController()
@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService service;

    @PostMapping("/")
    public PlayerModel postMethodName(@RequestBody PlayerModel pm) {

        return service.savePlayer(pm);
    }

}