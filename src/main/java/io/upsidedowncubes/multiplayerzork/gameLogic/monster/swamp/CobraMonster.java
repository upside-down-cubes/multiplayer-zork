package io.upsidedowncubes.multiplayerzork.gameLogic.monster.swamp;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;


/*
* This monster has low health but you can Weather gain or lose health or ...
* */
public class CobraMonster implements Monster {

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
//    public CobraMonster(){
//        super();
//        maxHP = 40;
//        hp = maxHP;
//        atk = 20;
//        name = "Cobra";
//        id = 5;
//    }
//
//    @Override
//    public void act(Player player) {
//        MessageOutput messageOut = MessageCenter.getUserMessageOut(player.getUsername());
//
//        int luck = rand.nextInt(5);
//        if(luck == 1){
//            attack( player );
//        }
//        else{
//            messageOut.printToUser(name + "'s attack misses'");
//        }
//    }
//
//    public void attack(Player p) {
//        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
//
//        messageOut.printToAll(name + " attack!");
//
//        int damage = atk + rand.nextInt(10);
//
//        p.loseHP( damage );
//        messageOut.printToAll("You took " + damage + " damage");
//
//    }
//
//    public void defend() {
//        System.out.println("DEBUG LOG: monster defends");
//    }
//
//    public void skill() {
//        System.out.println("DEBUG LOG: monster uses a skill");
//    }
}
