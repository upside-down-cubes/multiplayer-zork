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
    private final Random rand = new Random();
    private int numberOfBees = 10 + rand.nextInt(13);
    private final int MAX_BEES = numberOfBees;

    private final int atk = 1;
    private final int ID = 72;
    private boolean isDead = false;
    private final int giveExp = numberOfBees / 2;
    private final String name = "Bees";


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
        return giveExp;
    }

    @Override
    public String getName() {
        if (numberOfBees == 1) {
            return "Bee";
        }
        return numberOfBees + " " + name;
    }

    @Override
    public int receiveDamage(int amount) {
        int damage = amount / 2;
        numberOfBees -= damage;
        if (numberOfBees <= 0) {
            numberOfBees = MAX_BEES;
            isDead = true;
        }
        return damage;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void act(Player p) {
        int damage = 0;
        int numberOfAttacks = 0;
        for (int i = 0; i < numberOfBees; i++) {
            int attacked = attack();
            if (attacked > 1) {
                numberOfAttacks++;
            }
            damage += attacked;
        }

        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        messageOut.printToAll(numberOfAttacks + " " + name + " attacked " + p.getUsername());
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");

        p.loseHP(damage);
    }

    public int attack() {
        if (rand.nextInt(6) <= 1) {
            return 0;
        } else {
            int damage = atk;
            return damage;
        }
    }
}
