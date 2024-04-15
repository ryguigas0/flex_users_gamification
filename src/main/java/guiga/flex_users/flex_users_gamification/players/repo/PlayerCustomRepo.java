package guiga.flex_users.flex_users_gamification.players.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import guiga.flex_users.flex_users_gamification.players.PlayerModel;
import guiga.flex_users.flex_users_gamification.players.filter.AttributeFilter;
import guiga.flex_users.flex_users_gamification.players.filter.NumberRangeFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class PlayerCustomRepo {

    @PersistenceContext
    private EntityManager em;

    public List<PlayerModel> listPlayersByFilters(List<AttributeFilter> filters) {
        StringBuilder queryBuilder = new StringBuilder("select * from players p");

        queryBuilder.append(" where ");

        for (int i = 0; i < filters.size(); i++) {
            if (i != 0) {
                queryBuilder.append(" and ");
            }
            AttributeFilter af = filters.get(i);

            queryBuilder.append(af.toJsonbFilter());
        }

        Query q = em.createNativeQuery(queryBuilder.toString(), PlayerModel.class);
        return (List<PlayerModel>) q.getResultList();
    }
}
