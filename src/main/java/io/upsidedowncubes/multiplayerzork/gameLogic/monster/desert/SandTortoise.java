package io.upsidedowncubes.multiplayerzork.gameLogic.monster.desert;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;


/*
* This monster has low health but you can Weather gain or lose health or ...
* */
public class SandTortoise implements Monster {

    /*
     * Monster stats
     * */
    private int MAX_HP = 90;
    private int hp = MAX_HP;
    private int atk = 1;
    private String name = "Sand tortoise";
    private int ID = -1;
    private boolean isDead = true;

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;
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
        attack(p);
    }

    public void attack( Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (rand.nextInt(6)<= 1){
            messageOut.printToAll(name + " ate plastic and die!");
            hp = 0;
            isDead = true;
        }
        else{
            messageOut = MessageCenter.getUserMessageOut(p.getUsername());
            messageOut.printToAll(name + " attacked "+ p.getUsername());

            int damage = atk;
            p.loseHP( damage );
            messageOut.printToUser("You took " + damage + " damage");
        }

    }
}
