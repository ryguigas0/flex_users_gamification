package guiga.flex_users.flex_users_gamification.players;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepo extends CrudRepository<PlayerModel, Long> {

}
