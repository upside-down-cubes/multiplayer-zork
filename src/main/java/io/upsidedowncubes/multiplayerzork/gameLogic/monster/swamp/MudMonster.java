package io.upsidedowncubes.multiplayerzork.gameLogic.monster.swamp;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class MudMonster implements Monster {
    /*
     * Monster stats
     * */
    private final int MAX_HP = 20;
    private int hp = MAX_HP;
    private int atk = 3;
    private final String name = "Mud monster";
    private final int ID = 96;
    private boolean isDead = false;
    private final int giveExp = 3;

    int atkBoost = 0;
    boolean atkBoosted = false;

    /*
     * Extra var to keep track of
     * */

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
        return giveExp;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int receiveDamage(int amount) {
        hp -= amount;
        if (hp < 0) {
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
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (atkBoosted){
            atk += atkBoost;
            atkBoosted = false;
        }

        int rng = rand.nextInt(13);
        if (rng < 3){
            messageOut.printToAll(name + " charges its attack");
            if (atkBoost > 0){
                messageOut.printToAll(name + " accumulates its power even more");
                atk -= atkBoost;
            }
            atkBoosted = true;
            atkBoost += 4;
        }
        else if ( rng == 3 ){
            messageOut.printToAll(name + " consumes mud around itself");
            hp += 10;
            if (hp > MAX_HP) {
                hp = MAX_HP;
            }
            messageOut.printToAll(name + " gained 15 HP!");
        }
        else{
            attack(p);
        }

        if (! atkBoosted){
            atk -= atkBoost;
            atkBoost = 0;
        }

    }

    public void attack(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (rand.nextInt(4) <= 1) {
            // miss attack
            messageOut.printToAll(name + " missed the attack...");
        } else {
            messageOut = MessageCenter.getUserMessageOut(p.getUsername());
            messageOut.printToAll(name + " spat mud on" + p.getUsername());

            int damage = atk;
            p.loseHP(damage);
            messageOut.printToUser("You took " + damage + " damage");
            messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");
        }

    }
}


