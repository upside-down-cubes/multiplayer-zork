package io.upsidedowncubes.multiplayerzork.gameLogic.monster.bosses;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.format.number.money.MonetaryAmountFormatter;

import java.util.Random;

public class Golem implements Monster {

    /*
     * Monster stats
     * */
    private final int MAX_HP = 80;
    private int hp = MAX_HP;
    private final int atk = 4;
    private final String name = "Golem";
    private final int ID = 22;
    private boolean isDead = false;
    private final int giveExp = 30;

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;
    private int stage = 1;
    private int cycle = 0;
    private final boolean isFirst = true;

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
        if(stage == 1 && hp <0){
            // dai at stage one
            cycle = 0;
            hp = MAX_HP;
            stage = 2;
        }
        else if(stage != 1 && hp<0){
            // dia at stage two
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
        if (stage == 1){
            stageOne(p);
        }
        else{
            if(isFirst){ // First time attacking in stage 2
                MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
                messageOut.printToAll(name + "have regenerate back to full health and is randy to fight some more.");
            }
           stageTwo(p);
        }
        incrementCycle();
    }

    public void incrementCycle(){
        cycle++;
        if (cycle > 3){
            cycle = 0 ;
        }
    }


    public void stageOne(Player p){
       if(cycle==1){
           normalAttack(p);
       }
       else if(cycle == 2){
           slam(p);
       }
       else {
          throwRocks(p);
       }
    }
    public void stageTwo(Player p){
        if (cycle != 0){
            throwRocks(p);
        }
        else{
            tantrum(p);
        }

    }

    public void normalAttack(Player p){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        if (rand.nextInt(10) <= 2){
            messageOut.printToAll(name + "'s attack misses");
            return;
        }

        messageOut.printToAll(name + " attacked!");
        int damage = atk;

        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");

    }

    public void slam(Player p){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        if (rand.nextInt(10) <= 2){
            messageOut.printToAll(name + "'s attack misses");
            return;
        }

        messageOut.printToAll(name + " slammed on "+ p.getUsername());
        int damage = atk + 2 + rand.nextInt(6);

        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
    }

    public void throwRocks(Player p){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        if (rand.nextInt(10) <= 4){
            messageOut.printToAll(name + "'s attack misses");
            return;
        }

        int numRocks = 1+rand.nextInt(8);
        messageOut.printToAll(name + " throw "+numRocks + " rocks at "+p.getUsername());
        int damage = numRocks*2;
        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
    }

    public void tantrum(Player p){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        int recoil = rand.nextInt(11);
        messageOut.printToAll(name + " throws a tantrum and harts it self by " + recoil+ " hp");
        hp -= recoil;

        int damage = atk+3+rand.nextInt(6);
        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
    }

}
