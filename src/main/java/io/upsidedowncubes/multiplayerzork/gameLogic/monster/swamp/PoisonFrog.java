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

    private int MAX_HP = 2;
    private int hp = MAX_HP;
    private int atk = 98;
    private String name = "Bandits";
    private int ID = 4;

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
        return 1;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int receiveDamage(int amount) {

        return amount;
    }

    @Override
    public boolean isDead() {
        return false;
    }

        @Override
    public void act(Player player) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(player.getUsername());

            int luck = rand.nextInt(3);
        if(luck == 1){
            poison( player );
        }
        else{
            messageOut.printToUser("You got lucky");
            player.gainHP(30);
        }
    }

    public void poison(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        messageOut.printToAll(name + " poisoned" + p.getUsername());

        int damage = atk;

        p.loseHP( damage );
        messageOut.printToAll("You took " + damage + " damage");

    }
}
