package io.upsidedowncubes.multiplayerzork.webLogic.database;

import io.upsidedowncubes.multiplayerzork.webLogic.Controller.AddNewUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.security.sasl.AuthenticationException;

@Component
public class Sample {
    @Autowired
    PlayerRepository repository;

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Autowired
    InventoryRepository inventoryRepository;


    // example for loading player stats and inventory
//    @Bean
    public PlayerEntity loadPlayerStats() {
        PlayerEntity player = repository.findByUsername("admin");
        System.out.println("HP: " + player.getHp());
        System.out.println("Max HP: " + player.getMaxHp());
        System.out.println("Attack: " + player.getAttack());
        Integer inventoryID = player.getInventoryID();

        System.out.println("====================");

        InventoryEntity inventoryEntity = inventoryRepository.findByUsername("admin");
        System.out.println("Current Load: " + inventoryEntity.getCurrentLoad());
        System.out.println("Capacity: " + inventoryEntity.getCapacity());
        for (InventoryItemEntity inventoryItem : inventoryItemRepository.findAllByUsername("admin")) {
            System.out.println(inventoryItem.getItem() + ": " + inventoryItem.getQuantity());
        }

        return player;
    }

    @Bean
    // ignore the ugliness, for debugging
    public PlayerEntity updatePlayerStats() {
        PlayerEntity player = repository.findByUsername("admin");
        System.out.println("hp before: " + player.getHp());
        updateHp(player);
        System.out.println("hp after: " + player.getHp());

        System.out.println("====================");
        System.out.println("before taking anything");
        for (InventoryItemEntity inventoryItem : inventoryItemRepository.findAllByUsername("admin")) {
            System.out.println(inventoryItem.getItem() + ": " + inventoryItem.getQuantity());
        }
        takeItem("admin", "sword");
        System.out.println("after taking 1 sword");
        for (InventoryItemEntity inventoryItem : inventoryItemRepository.findAllByUsername("admin")) {
            System.out.println(inventoryItem.getItem() + ": " + inventoryItem.getQuantity());
        }
        takeItem("admin", "sword");
        System.out.println("after taking 2 swords");
        for (InventoryItemEntity inventoryItem : inventoryItemRepository.findAllByUsername("admin")) {
            System.out.println(inventoryItem.getItem() + ": " + inventoryItem.getQuantity());
        }

        System.out.println("====================");
        System.out.println("before dropping anything");
        for (InventoryItemEntity inventoryItem : inventoryItemRepository.findAllByUsername("admin")) {
            System.out.println(inventoryItem.getItem() + ": " + inventoryItem.getQuantity());
        }
        dropItem("admin", "sword");
        System.out.println("after dropping 1 sword");
        for (InventoryItemEntity inventoryItem : inventoryItemRepository.findAllByUsername("admin")) {
            System.out.println(inventoryItem.getItem() + ": " + inventoryItem.getQuantity());
        }
        dropItem("admin", "sword");
        System.out.println("after dropping 2 swords");
        for (InventoryItemEntity inventoryItem : inventoryItemRepository.findAllByUsername("admin")) {
            System.out.println(inventoryItem.getItem() + ": " + inventoryItem.getQuantity());
        }

        return player;
    }

    private void updateHp(PlayerEntity player) {
        player.setHp(player.getHp() + 5);
        if (player.getHp() > player.getMaxHp()) {
            player.setHp(player.getMaxHp());
        }
        repository.save(player);
    }


    private void takeItem(String username, String item) {
        InventoryEntity inventory = inventoryRepository.findByUsername(username);
        if (inventory.getCurrentLoad() >= inventory.getCapacity()) {
            return;
        }
        InventoryItemEntity inventoryItem = inventoryItemRepository.findByUsernameAndItem(username, item);
        if (inventoryItem == null) {
            inventoryItemRepository.save(new InventoryItemEntity(username, item));
        } else {
            inventoryItem.setQuantity(inventoryItem.getQuantity()+1);
            inventoryItemRepository.save(inventoryItem);
        }
        inventory.setCurrentLoad(inventory.getCurrentLoad()+1);
        inventoryRepository.save(inventory);
    }

    private void dropItem(String username, String item) {
        InventoryItemEntity inventoryItem = inventoryItemRepository.findByUsernameAndItem(username, item);
        if (inventoryItem == null) {
            return;
        } else if (inventoryItem.getQuantity() == 1) {
            System.out.println("deleting");
            inventoryItemRepository.delete(inventoryItem);
        } else {
            inventoryItem.setQuantity(inventoryItem.getQuantity()-1);
            inventoryItemRepository.save(inventoryItem);
        }
        InventoryEntity inventory = inventoryRepository.findByUsername(username);
        inventory.setCurrentLoad(inventory.getCurrentLoad()-1);
        inventoryRepository.save(inventory);
    }
}
