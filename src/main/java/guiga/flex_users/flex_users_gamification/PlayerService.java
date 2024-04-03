package guiga.flex_users.flex_users_gamification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepo repo;


    public PlayerModel savePlayer(PlayerModel newPm) {
        return repo.save(newPm);
    }
}
