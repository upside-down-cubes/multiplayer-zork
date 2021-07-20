package io.upsidedowncubes.multiplayerzork.gameLogic.monster.common;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class Goblin implements Monster {

    private final int MAX_HP = 30;
    private int hp = MAX_HP;
    private final int atk = 4;
    private final String name = "Goblin";
    private int ID = 30;
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
        return 2+rand.nextInt(3);
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
    }

    public void attack( Player p ){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        if (rand.nextInt(10) <= 2){
            messageOut.printToAll(name + "'s attack misses");
        }

        messageOut.printToAll(name + " attacked!");
        int damage = atk + rand.nextInt(4);

        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
    }

}
