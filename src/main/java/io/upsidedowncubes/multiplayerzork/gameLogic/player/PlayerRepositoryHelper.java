package io.upsidedowncubes.multiplayerzork.gameLogic.player;


import io.upsidedowncubes.multiplayerzork.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.database.PlayerRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.ContextAwareClass;

public class PlayerRepositoryHelper {
    private static final PlayerRepository PLAYER_REPOSITORY = (PlayerRepository) ContextAwareClass
            .getApplicationContext().getBean("playerRepository");

    public static PlayerEntity getPlayerEntity(String username) {
        return PLAYER_REPOSITORY.findByUsername(username);
    }

    public static boolean userExists(String username) {
        return PLAYER_REPOSITORY.findByUsername(username) != null;
    }

    public static void save(PlayerEntity p) {
        PLAYER_REPOSITORY.save(p);
    }
}
