package io.upsidedowncubes.multiplayerzork.messageoutput;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
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



    public static String getJson(String username, String message) {
        PlayerEntity player = PLAYER_REPOSITORY.findByUsername(username);
        InventoryEntity inventory = INVENTORY_REPOSITORY.findByUsername(username);
        try {
            return JsonConvertor.convert(GameLogicDTO.builder()
                    .content(message)
                    .attack(player.getAttack())
                    .hp(player.getHp())
                    .maxHp(player.getMaxHp())
                    .currentLoad(inventory.getCurrentLoad())
                    .capacity(inventory.getCapacity())
                    .roomDescription(OurWebSocketHandler.getGameByUser(username).getMap()
                            .getRoom(player.getRow(),player.getCol()).lookAround())
                    .build());
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return JsonConvertor.convert(GameLogicDTO.builder()
                    .content(message)
                    .attack(player.getAttack())
                    .hp(player.getHp())
                    .maxHp(player.getMaxHp())
                    .currentLoad(inventory.getCurrentLoad())
                    .capacity(inventory.getCapacity())
                    .roomDescription("not in game")
                    .build());
        }
    }
}
