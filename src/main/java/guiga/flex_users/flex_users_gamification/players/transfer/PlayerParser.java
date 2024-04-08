package guiga.flex_users.flex_users_gamification.players.transfer;

import guiga.flex_users.flex_users_gamification.players.PlayerModel;

public class PlayerParser {
    public static PlayerModel from(PlayerIn playerIn) {
        return PlayerModel.builder()
                .name(playerIn.getName())
                .points(playerIn.getPoints())
                .document(playerIn.getDocument())
                .build();

    }

    public static PlayerOut from(PlayerModel playerModel) {
        return PlayerOut.builder()
                .id(playerModel.getId())
                .name(playerModel.getName())
                .points(playerModel.getPoints())
                .document(playerModel.getDocument())
                .build();
    }
}
