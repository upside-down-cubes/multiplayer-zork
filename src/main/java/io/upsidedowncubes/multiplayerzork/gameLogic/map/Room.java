package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;

import java.util.*;

public class Room {
    // subject to be List
    List<Item> items;

    // will have only one
    Monster monster;

    // position of the room within the map
    int row;
    int col;
    GameMap gameMap;

    // set of available exits
    private final Set<Direction> exits;
    private String description;

    public Room(int row, int col, GameMap gameMap) {
        this.gameMap = gameMap;
        this.row = row;
        this.col = col;
        exits = new HashSet<>();
        items = new ArrayList<>();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    protected Room setExit(boolean north, boolean west, boolean east, boolean south) {
        if (north)
            exits.add(Direction.N);
        if (east)
            exits.add(Direction.E);
        if (west)
            exits.add(Direction.W);
        if (south)
            exits.add(Direction.S);
        return this;
    }

    protected Room setDescription(String des) {
        description = des;
        return this;
    }

    public Room addMonster(Monster mon) {
        monster = mon;
        return this;
    }

    public Room addItem(Item it) {
        items.add(it);
        return this;
    }

    public Monster getMonster() {
        return monster;
    }

    public void removeMonster() {
        monster = null;
    }

    public boolean canTake(Item item) {

        if (items.isEmpty()) { // if this room has no item
            return false;
        }
        // if this room has the same item as the inputted item
        for (Item roomItem : items) {
            if (roomItem.getItemID() == item.getItemID()) {
                return true;
            }
        }
        return false;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Set<Direction> getAvailableExit() {
        return exits;
    }

    public String lookAround() {

        StringBuilder msg = new StringBuilder();
        if (description != null) {
            msg.append(description);
        }
        if (monster != null) {
            msg.append(generateMessageMonster());
        }
        if (!items.isEmpty()) {
            for (Item item : items) {
                msg.append(generateMessageItem(item));
            }
        }
        if (msg.length() <= 0) {
            msg.append("There does not seem to be anything interesting in this room...\n");
        }

        msg.append("\nAvailable exit:");
        for (Direction dir : exits) {
            msg.append("   " + dir.name);
        }
        return msg.toString();
    }

    private String generateMessageItem(Item item) {
        Random rand = new Random();
        switch (rand.nextInt(3)) {
            case 0:
                return "Seems like a(n) " + item.getName() + " is lying on the ground...\n";
            case 1:
                return "You saw a(n) " + item.getName() + " is lying on the ground...\n";
            case 2:
                return "That " + item.getName() + " on the floor seems to be obtainable...\n";
            case 3:
                return "A(n) " + item.getName() + " appears to be located in the middle of the room...\n";
            default:
                return "When you entered the room, a(n) " + item.getName() + " piqued your interest...\n";
        }
    }

    private String generateMessageMonster() {
        Random rand = new Random();
        switch (rand.nextInt(3)) {
            case 0:
                return "A(n) " + monster.getName() + " appears to be in this room...\n";
            case 1:
                return "You feel a presence of the " + monster.getName() + " when you entered the room...\n";
            case 2:
                return "Seems like you saw a(n) " + monster.getName() + " in the other side of the room...\n";
            case 3:
                return "You saw a(n) " + monster.getName() + " standing still as if it is waiting for something...\n";
            default:
                return "A wild " + monster.getName() + " appears at the corner of the room...\n";
        }
    }


}