package io.upsidedowncubes.multiplayerzork.gameLogic.monster.ruins;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

/*
* The more he attacks the more tiered he become, but gains his power a attack harder again
* */
public class Bandits implements Monster {

    /*
    * Monster stats
    * */
    private int MAX_HP = 50;
    private int hp = MAX_HP;
    private int atk = 13;
    private String name = "Bandits";
    private int ID = 4;
    private boolean isDead = false;

    /*
    * Extra var to keep track of
    * */
    private int amountOfAttacks;
    private int luck;

    private final Random rand = new Random();


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
        return 2+rand.nextInt(2);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int receiveDamage(int amount) {
        hp -= amount;
        if(hp<0){
            isDead = true;
        }
        return amount;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void act(Player p) {
        attack(p);
        attackDecrease();
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

    private void attackDecrease(){
        amountOfAttacks++;
        atk -= amountOfAttacks;
        if (atk < 1){
            atk = 13;
            amountOfAttacks = 0;
        }

    }


}
