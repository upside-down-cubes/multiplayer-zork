package io.upsidedowncubes.multiplayerzork.gameLogic.monster.common;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class Wolves implements Monster {
    /*
     * Monster stats
     * */

    private final Random rand = new Random();
    private int numberOfWolves = 2 + rand.nextInt(2);
    private final int MAX_WOLVES = numberOfWolves;

    private final int MAX_HP = MAX_WOLVES * 6; // 6 hp per wolf
    private int hp = MAX_HP;
    private final int atk = 3;
    private int ID = 32;
    private boolean isDead = true;
    private final int giveExp = 2 * MAX_WOLVES;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getHP() {
        return 5 * numberOfWolves;
    }

    @Override
    public int getMaxHP() {
        return MAX_HP*MAX_WOLVES;
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
        if (numberOfWolves == 1){
            return "Wolf";
        }
        return numberOfWolves + " wolves";
    }

    @Override
    public int receiveDamage(int amount) {
        hp -= amount;

        // wolf --> 6 hp per wolf
        numberOfWolves = hp / 6;

        if(numberOfWolves<=0 || hp <= 0){
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

        int damage = 0;

        messageOut.printToAll( "The wolf pack attacked!");
        for (int i=0 ; i<numberOfWolves; i++){
            int attacked = attack();
            if (attacked > 1){
                messageOut.printToUser("You took " + attacked + " damage");
                messageOut.printToOthers( p.getUsername() + " took " + attacked + " damage");
            }
            else{
                messageOut.printToAll( "Wolf" + i + "'s attack misses..." );
            }
            damage += attacked;
        }

        p.loseHP( damage );
    }

    public int attack() {
        if (rand.nextInt(4)<= 1){
            return 0;
        }
        else{
            int damage = atk;
            return damage;
        }
    }
}
