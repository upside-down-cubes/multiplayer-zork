package io.upsidedowncubes.multiplayerzork.gameLogic.monster.swamp;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class Cobra implements Monster {

    private int MAX_HP = 40;
    private int hp = MAX_HP;
    private int atk = 15;
    private String name = "Cobra";
    private int ID = 93;
    private boolean isDead = true;
    private int giveExp = 3;

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;
    @Autowired
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
        attack(p);
    }

    public void attack( Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (rand.nextInt(8)<= 6){
            // miss attack
            messageOut.printToAll(name + " missed the attack... on " + p.getUsername());
        }
        else{
            messageOut = MessageCenter.getUserMessageOut(p.getUsername());
            messageOut.printToAll(name + " attacked!" + p.getUsername());

            int damage = atk;
            p.loseHP( damage );
            messageOut.printToUser("You took " + damage + " damage");
        }

    }

}
