package io.upsidedowncubes.multiplayerzork.gameLogic.monster.bosses;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class Ogre implements Monster {
    /*
     * Monster stats
     * */
    private final int MAX_HP = 80;
    private int hp = MAX_HP;
    private final int atk = 5;
    private final String name = "Ogre";
    private final int ID = 24;
    private boolean isDead = false;
    private final int giveExp = 20;

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
        if (hp > getMaxHP() * 0.6) {
            messageOut.printToAll("The Ogre shouted: Get outta my swamp!");
            normalAttack(p);
        } else if (hp > getMaxHP() * 0.3) {
            messageOut.printToAll("The Ogre shouted: Stop hitting me, you donkey!");
            hardAttack(p);
        } else {
            messageOut.printToAll("The Ogre shouted: You are hurting me!!!!");
            hardAttack(p);
        }
    }

    public void normalAttack(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        messageOut.printToAll(name + " attacked!");

        if (rand.nextInt(10) <= 2) {
            messageOut.printToAll(name + "'s attack misses");
            return;
        }

        int damage = atk;

        p.loseHP(damage);
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");

    }

    public void hardAttack(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        messageOut.printToAll(name + " slammed on " + p.getUsername());

        if (rand.nextInt(10) <= 2) {
            messageOut.printToAll(name + "'s attack misses");
            return;
        }

        int damage = atk + 2 + rand.nextInt(6);

        p.loseHP(damage);
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");
    }


}
