package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import io.upsidedowncubes.multiplayerzork.database.InventoryEntity;
import io.upsidedowncubes.multiplayerzork.database.InventoryItemEntity;
import io.upsidedowncubes.multiplayerzork.database.InventoryItemRepository;
import io.upsidedowncubes.multiplayerzork.database.InventoryRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.ContextAwareClass;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private static final InventoryItemRepository INVENTORY_ITEM_REPOSITORY = (InventoryItemRepository) ContextAwareClass
            .getApplicationContext().getBean("inventoryItemRepository");

    private static final InventoryRepository INVENTORY_REPOSITORY = (InventoryRepository) ContextAwareClass
            .getApplicationContext().getBean("inventoryRepository");

    // capacity at the moment
    private final int capacity;

    private int currentLoad;

    // actual structure that stores Item objects
    private final Map<Item, Integer> inventory;

    public Inventory(String username) {
        inventory = new HashMap<>();

        InventoryEntity inventoryEntity = INVENTORY_REPOSITORY.findByUsername(username);
        capacity = inventoryEntity.getCapacity();

        for (InventoryItemEntity inventoryItem : INVENTORY_ITEM_REPOSITORY.findAllByUsername(username)) {
            String itemName = inventoryItem.getItem();
            Item item = ItemFactory.getItem(itemName);
            int quan = inventoryItem.getQuantity();
            obtain(item, quan);
        }
    }

    // returns True is the inventory has no inputted Item
    public boolean hasNo(Item item) {
        return !inventory.containsKey(item);
    }

    // adds item to the inventory by the specified amount
    public boolean obtain(Item item, int amount) {
        // if the inventory is going to be over capacity then cant add; does nothing
        if ((amount + currentLoad) > capacity) {
            return false;
        }

        // actually add
        int oldAmount = inventory.getOrDefault(item, 0);
        inventory.put(item, oldAmount + amount);
        currentLoad += amount;
        return true;
    }

    public boolean obtain(Item item) {
        return obtain(item, 1);
    }

    // removes item from the inventory
    public boolean lose(Item item, int amount) {
        // if inventory is empty,
        // or the bag doesnt contain the item,
        // or the item you have has fewer than to lose (will reconsider this later)
        if (inventory.isEmpty() || hasNo(item) || inventory.get(item) < amount) {
            return false;
        } else {
            int oldAmount = inventory.get(item);
            if (oldAmount - amount == 0) {
                inventory.remove(item);
            } else {
                inventory.put(item, oldAmount - amount);
            }
            currentLoad -= amount;

            return true;
        }
    }

    public boolean lose(Item item) {
        return lose(item, 1);
    }

    // display the inventory as text
    public String viewInventory() {
        StringBuilder msg = new StringBuilder();
        msg.append("==== Inventory Detail ====");
        msg.append("\nBag Load " + currentLoad + " / " + capacity);

        if (inventory.isEmpty()) {
            msg.append("\n[     Nothing here    ]");
        } else {
            for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
                msg.append("\n[" + entry.getKey().getName() + "]: " + entry.getValue());
            }
        }
        msg.append("\n======================");
        return msg.toString();
    }

}