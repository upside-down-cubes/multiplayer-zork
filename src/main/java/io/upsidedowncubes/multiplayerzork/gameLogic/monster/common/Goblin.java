package io.upsidedowncubes.multiplayerzork.gameLogic.monster.common;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

public class Goblin implements Monster {

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
//    public Goblin(){
//        super();
//        maxHP = 30;
//        hp = maxHP;
//        atk = 4;
//        name = "Goblin";
//        id = 2;
//    }
//
//    @Override
//    public void act(Player p) {
//
//        attack(p);
//
//    }
//
//    public void attack( Player p ){
//        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
//
//        if (rand.nextInt(10) <= 2){
//            messageOut.printToAll(name + "'s attack misses");
//        }
//
//        messageOut.printToAll(name + " attacked!");
//        int damage = atk + rand.nextInt(4);
//
//        p.loseHP( damage );
//        messageOut.printToUser("You took " + damage + " damage");
//        messageOut.printToOthers( p.getUsername() + " took " + damage + " damage");
//    }


}
