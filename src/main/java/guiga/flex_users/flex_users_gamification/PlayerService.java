package guiga.flex_users.flex_users_gamification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guiga.flex_users.flex_users_gamification.transfer.PlayerIn;
import guiga.flex_users.flex_users_gamification.transfer.PlayerParser;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepo repo;

    public PlayerModel savePlayer(PlayerIn playerIn) {
        return repo.save(PlayerParser.from(playerIn));
    }

    public List<PlayerModel> listPlayers() {
        List<PlayerModel> output = new ArrayList<>();

        repo.findAll().forEach(pm -> output.add(pm));

        return output;
    }
}
