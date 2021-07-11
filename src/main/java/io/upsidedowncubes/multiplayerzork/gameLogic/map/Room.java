package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import upsidedowncubes.multiplayerzork.gameLogic.output.MessageOutput;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;


import java.util.*;

public class Room {
    // subject to be List
    Item item;

    // will have only one
    Monster monster;

    // position of the room within the map
    int row;
    int col;

    // set of available exits
    Set<Direction> exits;
    String description;

    public Room(int row, int col){
        this.row = row;
        this.col = col;
        exits = new HashSet<>();
    }

    protected void setExit(boolean north, boolean east, boolean west, boolean south){
        if (north)
            exits.add(Direction.N);
        if (east)
            exits.add(Direction.E);
        if (west)
            exits.add(Direction.W);
        if (south)
            exits.add(Direction.S);
    }

    protected void setDescription(String des){
        description = des;
    }

    public void lookAround(){
        StringBuilder msg = new StringBuilder();
        if (description != null){
            msg.append(description);
        }
        if (monster != null){
            msg.append( generateMessageMonster() );
        }
        if (item != null){
            msg.append( generateMessageItem() );
        }
        if (msg.length() <= 0){
            msg.append("There does not seem to be anything interesting in this room...");
        }
        MessageOutput.print(msg.toString());
    }

    private String generateMessageItem(){
        Random rand = new Random();
        switch ( rand.nextInt(3) ){
            case 0:
                return "Seems like a(n) " +  item.getName() + " is lying on the ground...";
            case 1:
                return "You saw a(n) " + item.getName() + " is lying on the ground...";
            case 2:
                return "That " + item.getName() + " on the floor seems to be obtainable...";
            case 3:
                return "A(n) " + item.getName() + " appears to be located in the middle of the room...";
            default:
                return "When you entered the room, a(n) " + item.getName() + " piqued your interest...";
        }
    }

    private String generateMessageMonster(){
        Random rand = new Random();
        switch ( rand.nextInt(3) ){
            case 0:
                return "A(n) " + monster.getName() + " appears to be in this room...";
            case 1:
                return "You feel a presence of the " + monster.getName() + " when you entered the room...";
            case 2:
                return "Seems like you saw a(n) " + monster.getName() + " in the other side of the room...";
            case 3:
                return "You saw a(n) " + monster.getName() + " standing still as if it is waiting for something...";
            default:
                return "A wild " + monster.getName() + " appears at the corner of the room...";
        }
    }


    protected void addMonster(Monster mon){
        monster = mon;
    }

    protected void addItem(Item it){
        item = it;
    }

    public Monster getMonster(){
        return monster;
    }

    public void removeMonster(){
        monster = null;
    }

    public Item getItem(){
        return item;
    }

    public boolean canTake(Item item){
        if (this.item == null){ // if this room has no item
            return false;
        }
        // if this room has the same item as the inputted item
        return this.item.getItemID() == item.getItemID() ;
    }

    public void removeItem(){
        item = null;
    }

}