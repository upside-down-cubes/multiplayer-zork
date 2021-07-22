package io.upsidedowncubes.multiplayerzork.gameLogic.monster.bosses;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

public class GiantScorpion implements Monster {
    /*
     * Monster stats
     * */
    private final int MAX_HP = 100;
    private int hp = MAX_HP;
    private final int atk = 4;
    private final String name = "Giant Scorpion";
    private final int ID = 20;
    private boolean isDead = false;
    private final int giveExp = 25;

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

        if (hp> MAX_HP/2){
            stageOne(p);
        }
        else{
            if(isFirst){ // First time attacking in stage 2
                MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
                messageOut.printToAll(name + "has release it's full power.");
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
            sting(p);
        }
        else {
            regenerate(p);
        }
    }
    public void stageTwo(Player p){
        if(cycle==1){
            acidSpray(p);
        }
        else if(cycle == 2){
            sting(p);
        }
        else {
            regenerate(p);
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

    public void sting(Player p){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        if (rand.nextInt(10) <= 2){
            messageOut.printToAll(name + "'s attack misses");
            return;
        }

        messageOut.printToAll(name + " has stung"+ p.getUsername());
        int damage = atk + 2 + rand.nextInt(6);

        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
    }

    public void regenerate(Player p){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        int regen = rand.nextInt(10);
        messageOut.printToAll(name + " has regenerated by "+regen + " hp");
        int damage = 0 ;

        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
    }

    public void acidSpray(Player p){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        int critical = 0;
        if(rand.nextInt(6)<=2){
            messageOut.printToAll(name + "Critical hit");
            critical = 2+rand.nextInt(5);
        }

        messageOut.printToAll(name + " has use Acid spray on" + p.getUsername());
        int damage = atk+critical;
        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
    }

}