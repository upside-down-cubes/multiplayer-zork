package io.upsidedowncubes.multiplayerzork.gameLogic.monster.grassland;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class Deer implements Monster {
    /*
     * Monster stats
     * */
    private int MAX_HP = 30;
    private int hp = MAX_HP;
    private int atk = 4;
    private String name = "Deer";
    private int ID = 74;
    private boolean isDead = true;
    private int giveExp = 3;

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;
    private Random rand;
    private boolean hasAntlers = true;

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
        if(hasAntlers && rand.nextInt(5)<=1){
            messageOut.printToAll(p.getUsername()+ " has broken the Deer's antlers.");
            hasAntlers = false;
            atk = 2;
        }
        attack(p);
    }

    public void attack( Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (rand.nextInt(5)<= 1){
            // miss attack
            messageOut.printToAll(name + " missed the attack... on " + p.getUsername());
        }
        else{
            messageOut = MessageCenter.getUserMessageOut(p.getUsername());
            messageOut.printToAll(name + " attacked!");

            int damage = atk;
            p.loseHP( damage );
            messageOut.printToUser("You took " + damage + " damage");
        }


    }
}
