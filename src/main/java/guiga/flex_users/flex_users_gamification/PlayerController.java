package guiga.flex_users.flex_users_gamification;

import org.springframework.web.bind.annotation.RestController;

import guiga.flex_users.flex_users_gamification.transfer.PlayerIn;
import jakarta.validation.Valid;

import java.util.List;

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
    public PlayerModel createPlayer(@Valid @RequestBody PlayerIn playerIn) {
        return service.savePlayer(playerIn);
    }

    @GetMapping("/")
    public List<PlayerModel> listPlayers() {
        return service.listPlayers();
    }
    

}
