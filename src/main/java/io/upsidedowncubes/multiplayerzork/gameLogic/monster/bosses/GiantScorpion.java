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
    private final int MAX_HP = 200;
    private int hp = MAX_HP;
    private int atk = 6;
    private final String name = "Giant Scorpion";
    private final int ID = 20;
    private boolean isDead = false;
    private final int giveExp = 20;

    /*
     * Extra var to keep track of
     * */
    private int cycle = 0;
    private boolean isFirst = true;
    private int atkBoost = 0;
    private boolean atkBoosted = false;

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
        if (atkBoosted){
            atk += atkBoost;
            atkBoosted = false;
        }

        if (hp > MAX_HP / 2) {
            stageOne(p);
        }
        else {
            if (isFirst) { // First time attacking in stage 2
                messageOut.printToAll(name + " has release it's full power.");
                isFirst = false;
            }
            stageTwo(p);
            messageOut.printToAll(name + " can't handle its own rage!");
            int recoil = rand.nextInt(5);
            messageOut.printToAll(name + " took " + recoil + " recoil damage");
            hp -= recoil;
            if (hp < 0) {
                isDead = true;
                return;
            }
        }

        if (! atkBoosted){
            atk -= atkBoost;
            atkBoost = 0;
        }

        setCycle();

    }

    public void setCycle() {
        cycle = rand.nextInt(3);
    }


    public void stageOne(Player p) {
        if (cycle == 1) {
            normalAttack(p);
        } else if (cycle == 2) {
            sting(p);
        } else {
            regenerate(p);
        }
    }

    public void stageTwo(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        if (cycle == 1) {
            acidSpray(p);
        } else if (cycle == 2) {
            sting(p);
        } else {
            int rng = rand.nextInt(3);
            messageOut.printToAll(name + " sharpen its stinger and gained " + rng + " extra ATK for the next turn!");
            setAtkBoost( 4 + rng );
        }
    }

    public void setAtkBoost(int amount){
        atkBoosted = true;
        atkBoost = amount;
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

    public void sting(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        messageOut.printToAll(name + " use its Stinger!");

        if (rand.nextInt(10) <= 2) {
            messageOut.printToAll(name + "'s attack misses");
            return;
        }

        messageOut.printToAll(name + " has stung" + p.getUsername());
        int damage = atk + 2 + rand.nextInt(6);

        p.loseHP(damage);
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");
    }

    public void regenerate(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        int regen = rand.nextInt(10);
        messageOut.printToAll(name + " has regenerated its health by " + regen + " hp");
        int damage = 0;

        p.loseHP(damage);
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");
    }

    public void acidSpray(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        messageOut.printToAll(name + " sprayed its acid on " + p.getUsername());

        int critical = 0;
        if (rand.nextInt(6) <= 2) {
            messageOut.printToAll("A Critical hit!");
            critical = 2 + rand.nextInt(5);
        }

        int damage = atk + critical;
        p.loseHP(damage);
        messageOut.printToUser("You took " + damage + " damage");
        messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");
    }

}
