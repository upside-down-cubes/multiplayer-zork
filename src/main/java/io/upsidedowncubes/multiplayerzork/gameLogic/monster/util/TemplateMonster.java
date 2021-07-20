package io.upsidedowncubes.multiplayerzork.gameLogic.monster.util;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class TemplateMonster implements Monster {
    /*
     * Monster stats
     * */
    private int MAX_HP = 50;
    private int hp = MAX_HP;
    private int atk = 13;
    private String name = "Temp";
    private int ID = -1;

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;
    private int luck;
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

    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void act(Player p) {
        attack(p);
    }

    public void attack( Player p) {
        luck = rand.nextInt(4);
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (luck <= 1){
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
