package io.upsidedowncubes.multiplayerzork.gameLogic.monster.ruins;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

/*
* The more he attacks the more tiered he become, but gains his power a attack harder again
* */
public class Bandits implements Monster {

    private int MAX_HP = 50;
    private int hp = MAX_HP;
    private int atk = 13;
    private String name = "Bandits";
    private int ID = 4;

    private int amountOfAttacks;

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
    public String getName() {
        return name;
    }

    @Override
    public void receiveDamage(int amount) {

    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void act(Player p) {

    }
//
//    @Override
//    public void act(Player player) {
//        MessageOutput messageOut = MessageCenter.getUserMessageOut(player.getUsername());
//
//        int luck = rand.nextInt(4);
//        if (luck <= 1){
//            messageOut.printToUser(name + "'s attack misses");
//        }
//        else{
//            attack( player );
//        }
//        amountOfAttacks++;
//        attackDecrease();
//    }
//
//    private void attackDecrease(){
//        atk -= amountOfAttacks;
//        if (atk < 1){
//            atk = 13;
//            amountOfAttacks = 1;
//        }
//
//    }
//
//    public void attack( Player p) {
//        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
//
//        messageOut.printToAll(name + " attacked!");
//
//        int damage = atk;
//        p.loseHP( damage );
//        messageOut.printToUser("You took " + damage + " damage");
//
//    }

}
