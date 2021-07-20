package io.upsidedowncubes.multiplayerzork.gameLogic.monster.desert;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class Mummy implements Monster {
    /*
     * Monster stats
     * */
    private int MAX_HP = 20;
    private int hp = MAX_HP;
    private int atk = 3;
    private String name = "Mummy";
    private int ID = 40;
    private boolean isDead = true;
    private int giveExp = 0;

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;

    private Random rand = new Random();

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getHP() {
        return hp;
    }

    @Override
    public int getMaxHP() {
        return MAX_HP;
    }

    @Override
    public int getAtk() {
        return atk;
    }

    @Override
    public int giveExp() {
        return giveExp;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void receiveDamage(int amount) {
        hp -= amount;
        if(hp<0){
            isDead = true;
        }
    }

    @Override
    public boolean isDead() {
        return isDead;
    }
    @Override
    public void act(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (rand.nextInt(5)<=1){
            int regen = rand.nextInt(6);
            messageOut.printToAll(name + " has regenerate it's hp by "+regen);
            hp += regen;
        }
        attack(p);
    }

    public void attack( Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        int damage  = 0;
        if (rand.nextInt(7)<= 1){
            damage = atk+rand.nextInt(6);
            messageOut.printToAll(name+" Critical hit!");
        }
        else{
            damage = atk;
        }

        messageOut.printToAll(name + " attacked on" + p.getUsername());
        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
    }
}
