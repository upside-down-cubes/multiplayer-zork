package io.upsidedowncubes.multiplayerzork.webLogic.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityUpdate {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Autowired
    InventoryRepository inventoryRepository;


    public void updateHp(String username, int amount) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setHp(player.getHp() + amount);
        if (player.getHp() > player.getMaxHp()) {
            player.setHp(player.getMaxHp());
        }
        playerRepository.save(player);
    }

    public void updateAtk(String username, int amount) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setHp(player.getHp() + amount);
        if (player.getHp() > player.getMaxHp()) {
            player.setHp(player.getMaxHp());
        }
        playerRepository.save(player);
    }


    public void takeItem(String username, String item, int quantity) {
        InventoryEntity inventory = inventoryRepository.findByUsername(username);
        if (inventory.getCurrentLoad() >= inventory.getCapacity()) {
            return;
        }
        InventoryItemEntity inventoryItem = inventoryItemRepository.findByUsernameAndItem(username, item);
        if (inventoryItem == null) {
            inventoryItemRepository.save(new InventoryItemEntity(username, item));
        } else {
            inventoryItem.setQuantity(inventoryItem.getQuantity()+quantity);
            inventoryItemRepository.save(inventoryItem);
        }
        inventory.setCurrentLoad(inventory.getCurrentLoad()+1);
        inventoryRepository.save(inventory);
    }

    public void dropItem(String username, String item, int quantity) {
        InventoryItemEntity inventoryItem = inventoryItemRepository.findByUsernameAndItem(username, item);
        if (inventoryItem == null) {
            return;
        } else if (inventoryItem.getQuantity() == 1) {
            System.out.println("deleting");
            inventoryItemRepository.delete(inventoryItem);
        } else {
            inventoryItem.setQuantity(inventoryItem.getQuantity()-quantity);
            inventoryItemRepository.save(inventoryItem);
        }
        InventoryEntity inventory = inventoryRepository.findByUsername(username);
        inventory.setCurrentLoad(inventory.getCurrentLoad()-quantity);
        inventoryRepository.save(inventory);
    }
}
