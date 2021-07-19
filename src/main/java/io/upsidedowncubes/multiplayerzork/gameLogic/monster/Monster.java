package io.upsidedowncubes.multiplayerzork.gameLogic.monster;


import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.List;
import java.util.Random;

public abstract class Monster {

    String name;
    int hp;
    int maxHP;
    int atk;
    List<Item> dropList;
    int id;

    Random rand = new Random();

    public int getID(){
        return id;
    }

    public int getHP() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public abstract void act(Player p);

    public void receiveDamage(int amount){
        hp -= amount;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public void gainHP(int amount){
        int amountHealed;
        if (hp + amount > maxHP){
            amountHealed = maxHP - hp;
        }
        else{
            amountHealed = amount;
        }
        MessageOutput.printToAll(getName() + " gained " + amountHealed + " HP");
        hp += amountHealed;
    }

}
