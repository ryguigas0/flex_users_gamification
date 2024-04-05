package guiga.flex_users.flex_users_gamification.transfer;

import guiga.flex_users.flex_users_gamification.PlayerModel;

public class PlayerParser {
    public static PlayerModel from(PlayerIn playerIn) {
        return PlayerModel.builder()
                .points(playerIn.getPoints())
                .document(playerIn.getDocument()).build();

    }
}
