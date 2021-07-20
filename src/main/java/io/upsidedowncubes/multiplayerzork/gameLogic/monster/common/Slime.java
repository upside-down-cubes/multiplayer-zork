package io.upsidedowncubes.multiplayerzork.gameLogic.monster.common;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class Slime implements Monster {
    /*
     * Monster stats
     * */
    private int MAX_HP = 20;
    private int hp = MAX_HP;
    private int atk = 2;
    private String name = "Slime";
    private int ID = 31;
    private boolean isDead = true;

    /*
     * Extra var to keep track of
     * */
    private Random rand;

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
        return 1+rand.nextInt(1);
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

        int rng = rand.nextInt(10);
        if (rng == 0){
            messageOut.printToAll(name + " failed to attack...");
        }
        else if (rng <= 4){
            attack(p);
        }
        else{
            messageOut.printToAll(name + " performed a continuous attack!");
            attack(p);
            attack(p);
            attack(p);

        }

    }

    public void attack( Player p ){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        if (rand.nextInt(10) <= 2){
            messageOut.printToAll(name + "'s attack misses");
        }

        messageOut.printToAll(name + " attacked!");
        int damage = atk + rand.nextInt(2);

        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
    }


}
