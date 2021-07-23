package io.upsidedowncubes.multiplayerzork.gameLogic.monster.swamp;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;


/*
 * This monster has low health but you can Weather gain or lose health or ...
 * */
public class PoisonFrog implements Monster {

    private final int MAX_HP = 16;
    private int hp = MAX_HP;
    private final int atk = 10;
    private final String name = "Poison frog";
    private final int ID = 4;
    private boolean isDead = false;

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
        return 6;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int receiveDamage(int amount) {
        hp -= amount;
        if (hp <= 0){
            isDead = true;
        }
        return amount;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void act(Player player) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(player.getUsername());

        int luck = rand.nextInt(3);
        if (luck == 1) {
            poison(player);
        } else {
            messageOut.printToUser("You got lucky, the frog attacked with a beneficial acid");
            player.gainHP(10+rand.nextInt(21));
        }
    }

    public void poison(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        messageOut.printToAll(name + " poisoned " + p.getUsername());

        int damage = atk;

        p.loseHP(damage);
        messageOut.printToAll("You took " + damage + " damage");

    }
}
