package io.upsidedowncubes.multiplayerzork.gameLogic.monster.desert;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;


/*
* This monster has low health but you can Weather gain or lose health or ...
* */
public class SandTortoise implements Monster {

    /*
     * Monster stats
     * */
    private final int MAX_HP = 40;
    private int hp = MAX_HP;
    private final int atk = 3;
    private final String name = "Sand tortoise";
    private int ID = -1;
    private boolean isDead = false;

    /*
     * Extra var to keep track of
     * */
    private final Random rand = new Random();
    private int damageReduction = 0;

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
        return 1+rand.nextInt(3);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int receiveDamage(int amount) {
        if (amount - damageReduction <= 0){
            return 0;
        }

        hp -= amount - damageReduction;
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
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        int rng = rand.nextInt(90);
        if (rng <= 50){
            int damage = atk + rand.nextInt(3);
            p.loseHP( damage );
            messageOut.printToUser("You took " + damage + " damage");
            messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
        }
        else if (rng <= 75){
            messageOut.printToAll(name + " embraces for the incoming hit!");
            damageReduction = 5;
        }
        else{
            messageOut.printToAll(name + " absorbs sand around itself");
            if (rng == 80){
                messageOut.printToAll(name + " ate plastic from beneath the sand and die!");
                hp = 0;
                isDead = true;
            }
            else{
                hp += 15;
                if (hp > MAX_HP){
                    hp = MAX_HP;
                }
                messageOut.printToAll(name + " gained 15 HP!");
            }

        }

    }

}
