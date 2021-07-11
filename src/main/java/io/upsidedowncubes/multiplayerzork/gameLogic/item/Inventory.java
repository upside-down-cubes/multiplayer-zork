package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    // capacity at the moment
    private int capacity;

    private int currentLoad;

    // actual structure that stores Item objects
    private Map<Item, Integer> inventory;


    public Inventory(){
        // initial capacity is 20
        capacity = 20;

        currentLoad = 0;
        inventory = new HashMap<>();

    }

    // returns True is the inventory has no inputted Item
    public boolean hasNo(Item item){
        return ! inventory.containsKey( item );
    }

    // adds item to the inventory by the specified amount
    public boolean obtain(Item item, int amount){
        // if the inventory is going to be over capacity then cant add; does nothing
        if (amount + currentLoad > capacity){
            return false;
        }

        // actually add
        int oldAmount = inventory.getOrDefault(item, 0);
        inventory.put(item, oldAmount + amount);
        currentLoad += amount;
        return true;
    }

    public boolean obtain(Item item){
        return obtain(item, 1);
    }

    // removes item from the inventory
    public boolean lose(Item item, int amount){
        // if inventory is empty,
        // or the bag doesnt contain the item,
        // or the item you have has fewer than to lose (will reconsider this later)
        if (inventory.isEmpty() || hasNo(item) || inventory.get(item) < amount){
            return false;
        }
        else{
            int oldAmount = inventory.get(item);
            if (oldAmount - amount == 0){
                inventory.remove(item);
            }
            else {
                inventory.put(item, oldAmount - amount);
            }
            currentLoad -= amount;

            return true;
        }
    }

    public boolean lose(Item item){
        return lose(item, 1);
    }

    // display the inventory as text
    public void viewInventory(){
        MessageOutput.print("==== Inventory Detail ====");
        if (inventory.isEmpty()){
            MessageOutput.print("\n==     Nothing here    ==");
        }
        else{
            for (Map.Entry<Item, Integer> entry : inventory.entrySet()){
                MessageOutput.print( "[" + entry.getKey().getName() + "]: " + entry.getValue());
            }
        }
        MessageOutput.print("\n==========================");
    }

}