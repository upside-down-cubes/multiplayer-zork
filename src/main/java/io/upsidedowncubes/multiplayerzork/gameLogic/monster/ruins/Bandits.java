package io.upsidedowncubes.multiplayerzork.gameLogic.monster.ruins;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.Random;

/*
 * The more he attacks the more tiered he become, but gains his power a attack harder again
 * */
public class Bandits implements Monster {

    /*
     * Monster stats
     * */
    String uniqueName[] = new String[]{
            "Sao_Bang_Pho888", "Mike Tyson", "Strawberry Milkshake", "TixGamer", "Rick R. Roll",
            "John Cena", "Vee R. Phong", "CHAD"
    };
    private final Random rand = new Random();
    private final int MAX_HP = 50;
    private int hp = MAX_HP;
    private int atk = 13;
    private final String name = "Bandits";
    private final String nickname = uniqueName[rand.nextInt( uniqueName.length )];
    private final int ID = 4;
    private boolean isDead = false;

    /*
     * Extra var to keep track of
     * */
    private int amountOfAttacks;
    private int luck;




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
        return 4 + rand.nextInt(4);
    }

    @Override
    public String getName() {
        return nickname + " the " +name;
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
        attackDecrease();
    }

    public void attack(Player p) {
        luck = rand.nextInt(4);
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        if (luck <= 1) {
            // miss attack
            messageOut.printToAll(name + " missed the attack...");
        } else {
            messageOut.printToAll(name + " attacked!");

            int damage = atk;
            p.loseHP(damage);
            messageOut.printToUser("You took " + damage + " damage");
            messageOut.printToOthers(p.getUsername() + " took " + damage + " damage");
        }

    }

    private void attackDecrease() {
        amountOfAttacks++;
        atk -= amountOfAttacks;
        if (atk < 1) {
            atk = 13;
            amountOfAttacks = 0;
        }

    }


}
