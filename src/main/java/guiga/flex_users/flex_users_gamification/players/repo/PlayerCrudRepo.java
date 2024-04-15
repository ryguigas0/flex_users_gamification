package guiga.flex_users.flex_users_gamification.players.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import guiga.flex_users.flex_users_gamification.players.PlayerModel;

@Repository
public interface PlayerCrudRepo extends CrudRepository<PlayerModel, Long> {
}
