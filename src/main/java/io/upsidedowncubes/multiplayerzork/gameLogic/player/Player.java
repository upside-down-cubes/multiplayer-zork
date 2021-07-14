package io.upsidedowncubes.multiplayerzork.gameLogic.player;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.InventoryItemRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.database.InventoryRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class Player {
    @Autowired
    PlayerRepository repository;

    String username;
    private int hp;
    private int maxHP;

    private int atk;

    private final double missRate = 0.1;
    private final double critRate = 0.1;
    private final double critMultiplier = 1.5;
    private final Random rand = new Random();


    private Inventory bag;

    public Player(){
        hp = 50;
        maxHP = 50;
        atk = 5;

        bag = new Inventory();
    }

    public Player(String username){
        PlayerEntity player = repository.findByUsername(username);

        this.username = username;
        this.hp = player.getHp();
        this.maxHP = player.getMaxHp();
        this.atk = player.getAttack();

        bag = new Inventory(username);
    }


    public int gainHP(int amount){
        int amountHealed;
        if (hp + amount > maxHP){
            amountHealed = maxHP - hp;
        }
        else{
            amountHealed = amount;
        }
        MessageOutput.printToAll( username + " gained " + amountHealed + " HP");
        hp += amountHealed;
        return amountHealed;
    }

    public void loseHP(int amount){
        hp -= amount;
    }

    public boolean isFullHP(){ return hp == maxHP; }

    public Inventory getBag(){ return bag; }

    public void viewStatus(){
        MessageOutput.printToAll("==== Player Information ====");
        MessageOutput.printToAll("HP: " + hp + "/" + maxHP);
        MessageOutput.printToAll("ATK: " + atk);
        MessageOutput.printToAll("============================");
    }

    public int attack(Weapon wp){
        int damage;

        // determines if the attack lands
        if (attackMiss()){
            MessageOutput.printToAll( username + "'s attack misses...");
            damage = -1;
        }
        else{
            // determines the damage when the attack lands
            double damageMultiplier = 1.0;
            if (performCrit()){
                damageMultiplier = getCritMultiplier();
                MessageOutput.printToAll("A Critical Hit!!!");
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
        MessageOutput.printToAll( username + " gained " + amount + " attack stat" );
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


    // TODO: figure out how to deal with gameover
    public boolean isDead(){
        return hp <= 0;
    }

    public void check(){
        if (isDead()){
            MessageOutput.printToAll("***** Game Over *****");
            //game.setGameState(false);
            //MessageOutput.printToAll("(Returned to Menu mode)");
        }
    }

}
