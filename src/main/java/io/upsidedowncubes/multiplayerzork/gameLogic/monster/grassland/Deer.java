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
    private final int MAX_HP = 35;
    private int hp = MAX_HP;
    private int atk = 4;
    private final String name = "Deer";
    private final int ID = 74;
    private boolean isDead = false;
    private final int giveExp = 5;

    /*
     * Extra var to keep track of
     * */
    private final Random rand = new Random();
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
        attack(p);
        if (hasAntlers && rand.nextInt(5) <= 1) {
            messageOut.printToAll(name + " has broken the its own antlers.");
            hasAntlers = false;
            atk = 2;
        }

    }

    public void attack(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        messageOut.printToAll(name + " attacked!");
        if (rand.nextInt(5) <= 1) {
            // miss attack
            messageOut.printToAll(name + " misses its attack");
        } else {
            messageOut = MessageCenter.getUserMessageOut(p.getUsername());
            messageOut.printToAll(name + " attacked!" + p.getUsername());

            int damage = atk;
            p.loseHP(damage);
            messageOut.printToUser("You took " + damage + " damage");
            messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");
        }


    }
}
