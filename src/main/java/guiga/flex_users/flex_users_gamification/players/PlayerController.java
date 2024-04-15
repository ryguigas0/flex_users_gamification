package guiga.flex_users.flex_users_gamification.players;

import org.springframework.web.bind.annotation.RestController;

import guiga.flex_users.flex_users_gamification.players.transfer.PlayerIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerListFilterIn;
import guiga.flex_users.flex_users_gamification.players.transfer.PlayerOut;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController()
@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService service;

    @PostMapping("/")
    public PlayerOut createPlayer(@Valid @RequestBody PlayerIn playerIn) {
        return service.savePlayer(playerIn);
    }

    @GetMapping("/")
    public List<PlayerOut> listPlayers(@RequestBody Map<String, String> filterMap) {
        return service.listPlayers(new PlayerListFilterIn(filterMap));
    }

}
