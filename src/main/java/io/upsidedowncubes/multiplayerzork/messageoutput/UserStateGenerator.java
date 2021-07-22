package io.upsidedowncubes.multiplayerzork.messageoutput;

import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.GameLogicDTO;
import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.JsonConvertor;
import io.upsidedowncubes.multiplayerzork.webLogic.database.InventoryEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.InventoryRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.ContextAwareClass;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;

public class UserStateGenerator {
    private final static PlayerRepository PLAYER_REPOSITORY = (PlayerRepository) ContextAwareClass
            .getApplicationContext().getBean("playerRepository");
    private final static InventoryRepository INVENTORY_REPOSITORY = (InventoryRepository) ContextAwareClass
            .getApplicationContext().getBean("inventoryRepository");


    public static String getJson(String username, String message, int gameStatus) {
        PlayerEntity player = PLAYER_REPOSITORY.findByUsername(username);
        InventoryEntity inventory = INVENTORY_REPOSITORY.findByUsername(username);
        try {
            return JsonConvertor.convert(GameLogicDTO.builder()
                    .type(gameStatus)
                    .content(message)
                    .attack(player.getAttack())
                    .hp(player.getHp())
                    .maxHp(player.getMaxHp())
                    .exp(player.getExp())
                    .maxExp(player.getMaxExp())
                    .currentLoad(inventory.getCurrentLoad())
                    .capacity(inventory.getCapacity())
                    .otherUsers(OurWebSocketHandler.getAllUsersInSameSession(username))
                    .mapName(OurWebSocketHandler.getGameByUser(username).getMap().getMapName())
                    .roomDescription(OurWebSocketHandler.getGameByUser(username).getMap()
                            .getRoom(player.getRow(), player.getCol()).lookAround())
                    .build());
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return JsonConvertor.convert(GameLogicDTO.builder()
                    .type(gameStatus)
                    .content(message)
                    .attack(player.getAttack())
                    .hp(player.getHp())
                    .maxHp(player.getMaxHp())
                    .exp(player.getExp())
                    .maxExp(player.getMaxExp())
                    .currentLoad(inventory.getCurrentLoad())
                    .capacity(inventory.getCapacity())
                    .otherUsers(OurWebSocketHandler.getAllUsersInSameSession(username))
                    .mapName("N/A")
                    .roomDescription("N/A")
                    .build());
        }
    }
}
