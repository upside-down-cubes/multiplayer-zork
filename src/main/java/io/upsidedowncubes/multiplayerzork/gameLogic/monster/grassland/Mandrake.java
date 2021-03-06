package io.upsidedowncubes.multiplayerzork.gameLogic.monster.grassland;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class Mandrake implements Monster {
    /*
     * Monster stats
     * */
    private final int MAX_HP = 15;
    private int hp = MAX_HP;
    private final int atk = 5;
    private final String name = "Mandrake";
    private final int ID = 78;
    private boolean isDead = false;
    private final int giveExp = 3;

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

        messageOut.printToAll(name + " screeched! : aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!");
        int damage = atk + rand.nextInt(3);
        p.loseHP(damage);
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");
    }
}
