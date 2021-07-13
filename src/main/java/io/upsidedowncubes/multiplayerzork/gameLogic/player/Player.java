package io.upsidedowncubes.multiplayerzork.gameLogic.player;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class Player {

    private String username;
    private int session_id;

    private int hp;
    private int maxHP;

    private int atk;

    private double missRate;
    private double critRate;
    private double critMultiplier;
    private Random rand = new Random();

    @Autowired
    Game game;

    private Inventory bag;

    public Player(){
        hp = 50;
        maxHP = 50;
        atk = 5;

        missRate = 0.1;
        critRate = 0.1;
        critMultiplier = 1.5;
        bag = new Inventory();
    }


    public void gainHP(int amount){
        int amountHealed;
        if (hp + amount > maxHP){
            amountHealed = maxHP - hp;
        }
        else{
            amountHealed = amount;
        }
        MessageOutput.print( username + " gained " + amountHealed + " HP");
        hp += amountHealed;
    }

    public void loseHP(int amount){
        hp -= amount;
    }

    public boolean isFullHP(){ return hp == maxHP; }

    public Inventory getBag(){ return bag; }

    public void viewStatus(){
        MessageOutput.print("==== Player Information ====");
        MessageOutput.print("HP: " + hp + "/" +maxHP);
        MessageOutput.print("ATK: " + atk);
        MessageOutput.print("============================");
    }

    public int attack(Weapon wp){
        int damage;

        // determines if the attack lands
        if (attackMiss()){
            MessageOutput.print( username + "'s attack misses...");
            damage = -1;
        }
        else{
            // determines the damage when the attack lands
            double damageMultiplier = 1.0;
            if (performCrit()){
                damageMultiplier = getCritMultiplier();
                MessageOutput.print("A Critical Hit!!!");
            }
            damage = (int) Math.round( getATK(wp) * damageMultiplier );
        }
        return damage;
    }

    public int getATK( Weapon wp ){
        if (wp == null){
            return atk;
        }
        return atk + wp.getAttackStat();
    }

    public void gainATK(int amount){
        MessageOutput.print( username + " gained " + amount + " attack stat" );
        atk += amount;
    }

    public boolean performCrit(){
        return rand.nextDouble() < critRate;
    }

    public boolean attackMiss(){
        return rand.nextDouble() < missRate;
    }

    public double getCritMultiplier() {
        return critMultiplier;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public void check(){
        if (isDead()){
            MessageOutput.print("***** Game Over *****");
            game.setGameState(false);
            MessageOutput.print("(Returned to Menu mode)");
        }
    }

}
