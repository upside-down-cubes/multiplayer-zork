package io.upsidedowncubes.multiplayerzork.gameLogic.monster.snowland;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class SnowLeopard implements Monster {
    /*
     * Monster stats
     * */
    private final int MAX_HP = 50;
    private int hp = MAX_HP;
    private final int atk = 13;
    private final String name = "Snow leopard";
    private final int ID = 59;
    private boolean isDead = false;
    private final int giveExp = 4;

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
        attack(p);
    }

    public void attack(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (rand.nextInt(4) <= 1) {
            // miss attack
            messageOut.printToAll(name + " missed the attack... on " + p.getUsername());
        } else if (rand.nextInt(3) == 1) {
            // quick attack
            for (int i = 0; i < 2; i++) {
                messageOut = MessageCenter.getUserMessageOut(p.getUsername());
                messageOut.printToAll(name + " attacked!");

                int damage = atk;
                p.loseHP(damage);
                messageOut.printToUser("You took " + damage + " damage");
            }
        } else {
            // normal attack
            messageOut = MessageCenter.getUserMessageOut(p.getUsername());
            messageOut.printToAll(name + " attacked!");

            int damage = atk;
            p.loseHP(damage);
            messageOut.printToUser("You took " + damage + " damage");
        }

    }

}
