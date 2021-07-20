package io.upsidedowncubes.multiplayerzork.gameLogic.monster.grassland;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class Bees implements Monster {
    /*
     * Monster stats
     * */
    private int MAX_HP = 1; // This doesn't matter
    private int hp = MAX_HP;
    private int atk = 1;
    private int ID = 72;
    private boolean isDead = true;
    private int giveExp = 1;
    private String name = "Bees";

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;
    private Random rand;
    private int numberOfBees= 1+rand.nextInt(13);
    private int MAX_BEES= numberOfBees;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getHP() {
        return numberOfBees;
    }

    @Override
    public int getMaxHP() {
        return MAX_BEES;
    }

    @Override
    public int getAtk() {
        return atk;
    }

    @Override
    public int giveExp() {
        return rand.nextInt(MAX_BEES);
    }

    @Override
    public String getName() {
        return numberOfBees+" "+name;
    }

    @Override
    public void receiveDamage(int amount) {
        numberOfBees -= amount;
        if(numberOfBees<=0){
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
        for (int i=0 ; i<numberOfBees; i++){
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
        if (rand.nextInt(6)<= 1){
            return 0;
        }
        else{
            int damage = atk;
            return damage;
        }
    }
}
