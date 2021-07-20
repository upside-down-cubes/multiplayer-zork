package io.upsidedowncubes.multiplayerzork.gameLogic.monster.common;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class Wolves implements Monster {
    /*
     * Monster stats
     * */
    private int MAX_HP = 5; // This doesn't matter
    private int hp = MAX_HP;
    private int atk = 3;
    private int ID = 32;
    private boolean isDead = true;
    private int giveExp = 0;
    private String name = "Wolves";

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;

    private Random rand = new Random();

    private int numberOfWolves = 1+rand.nextInt(3);
    private int MAX_WOLVES = numberOfWolves;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getHP() {
        return 5*numberOfWolves;
    }

    @Override
    public int getMaxHP() {
        return MAX_HP*MAX_WOLVES;
    }

    @Override
    public int getAtk() {
        return atk;
    }

    @Override
    public int giveExp() {
        return 2*MAX_WOLVES;
    }

    @Override
    public String getName() {
        return numberOfWolves+" "+name;
    }

    @Override
    public void receiveDamage(int amount) {
        if(rand.nextInt(10)<amount){
            numberOfWolves--;
        }
        if(numberOfWolves<=0){
            isDead = true;
        }
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void act(Player p) {
        int damage = 0;
        int numberOfAttacks = 0;
        for (int i=0 ; i<numberOfWolves; i++){
            int attacked = attack(p);
            if(attacked > 1){
                numberOfAttacks++;
            }
            damage+= attacked;
        }

        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        messageOut.printToAll(numberOfAttacks+" "+name+ " attacked "+ p.getUsername());
        messageOut.printToUser("You took " + damage + " damage");

        p.loseHP( damage );
    }

    public int attack( Player p) {
        if (rand.nextInt(4)<= 1){
            return 0;
        }
        else{
            int damage = atk;
            return damage;
        }
    }
}
