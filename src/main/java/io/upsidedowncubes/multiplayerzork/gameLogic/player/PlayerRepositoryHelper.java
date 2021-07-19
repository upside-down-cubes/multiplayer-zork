package io.upsidedowncubes.multiplayerzork.gameLogic.player;


import io.upsidedowncubes.multiplayerzork.webLogic.database.InventoryRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.ContextAwareClass;

public class PlayerRepositoryHelper {
    private static final PlayerRepository PLAYER_REPOSITORY = (PlayerRepository) ContextAwareClass
            .getApplicationContext().getBean("playerRepository");

    private final static InventoryRepository INVENTORY_REPOSITORY = (InventoryRepository) ContextAwareClass
            .getApplicationContext().getBean("inventoryRepository");

    public static PlayerEntity getPlayerEntity(String username){
        return PLAYER_REPOSITORY.findByUsername(username);
    }

    public static boolean userExists(String username){
        return PLAYER_REPOSITORY.findByUsername(username) != null;
    }

    public static void save(PlayerEntity p){
        PLAYER_REPOSITORY.save(p);
    }
}
