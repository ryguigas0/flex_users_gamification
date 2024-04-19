package guiga.flex_users.flex_users_gamification.players.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guiga.flex_users.flex_users_gamification.players.PlayerModel;

@Repository
public interface PlayerCrudRepo extends CrudRepository<PlayerModel, Long> {

    @Modifying
    @Query(value = "update players set document = jsonb_set(document\\:\\:jsonb , concat('{', :key, '}')\\:\\:text[], to_jsonb(:value), true) where campaign_id = :campaignId", nativeQuery = true)
    void addNewDocumentKey(@Param("key") String key, @Param("value") Object value, @Param("campaignId") Long campaignId);

    @Modifying
    @Query(value = "update players set document = document\\:\\:jsonb - ':deleteKey' where campaign_id = :campaignId", nativeQuery = true)
    void deleteDocumentKey(@Param("deleteKey") String deleteKey, @Param("campaignId") Long campaignId);
}
