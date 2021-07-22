package io.upsidedowncubes.multiplayerzork.database;

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

    public void updateMaxHp(String username, int amount) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setMaxHp(player.getMaxHp() + amount);
        playerRepository.save(player);
    }

    public void updateHp(String username, int amount) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setHp(player.getHp() + amount);
        if (player.getHp() > player.getMaxHp()) {
            player.setHp(player.getMaxHp());
        }
        playerRepository.save(player);
    }

    public void setHp(String username, int amount) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setHp(amount);
        if (player.getHp() > player.getMaxHp()) {
            player.setHp(player.getMaxHp());
        }
        playerRepository.save(player);
    }

    public void updateAtk(String username, int amount) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setAttack(player.getAttack() + amount);
        playerRepository.save(player);
    }

    public void updateMaxExp(String username) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setMaxExp(player.getMaxExp() * 2);
        playerRepository.save(player);
    }

    public boolean updateExp(String username, int amount) {
        boolean ret = false;
        PlayerEntity player = playerRepository.findByUsername(username);

        int currentExp = player.getExp();
        int currentMaxExp = player.getMaxExp();
        if (currentExp + amount >= currentMaxExp) {
            int diff = (currentExp + amount) - currentMaxExp;
            player.setMaxExp(player.getMaxExp() * 2);
            player.setExp(diff);
            ret = true;
        } else {
            player.setExp(currentExp + amount);
        }
        playerRepository.save(player);
        return ret;
    }

    public void setExp(String username, int amount) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setExp(amount);
        playerRepository.save(player);
    }

    public void updateLoc(String username, int row, int col) {
        PlayerEntity player = playerRepository.findByUsername(username);
        player.setRow(row);
        player.setCol(col);
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
            inventoryItem.setQuantity(inventoryItem.getQuantity() + quantity);
            inventoryItemRepository.save(inventoryItem);
        }
        inventory.setCurrentLoad(inventory.getCurrentLoad() + 1);
        inventoryRepository.save(inventory);
    }

    public void dropAllItems(String username) {
        inventoryItemRepository.deleteAllByUsername(username);
        InventoryEntity inventoryEntity = inventoryRepository.findByUsername(username);
        inventoryEntity.setCurrentLoad(0);
        inventoryRepository.save(inventoryEntity);
    }

    public void dropItem(String username, String item, int quantity) {
        InventoryItemEntity inventoryItem = inventoryItemRepository.findByUsernameAndItem(username, item);
        if (inventoryItem == null) {
            return;
        } else if (inventoryItem.getQuantity() == 1) {
            System.out.println("deleting");
            inventoryItemRepository.delete(inventoryItem);
        } else {
            inventoryItem.setQuantity(inventoryItem.getQuantity() - quantity);
            inventoryItemRepository.save(inventoryItem);
        }
        InventoryEntity inventory = inventoryRepository.findByUsername(username);
        inventory.setCurrentLoad(inventory.getCurrentLoad() - quantity);
        inventoryRepository.save(inventory);
    }

}
