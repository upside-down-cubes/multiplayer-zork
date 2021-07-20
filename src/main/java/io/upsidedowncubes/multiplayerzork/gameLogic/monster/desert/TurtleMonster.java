package io.upsidedowncubes.multiplayerzork.gameLogic.monster.desert;

import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;


/*
* This monster has low health but you can Weather gain or lose health or ...
* */
public class TurtleMonster implements Monster {

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
//    public TurtleMonster(){
//        super();
//        maxHP = 900;
//        hp = maxHP;
//        atk = 1;
//        name = "Turtle";
//    }
//
//    @Override
//    public void act(Player player) {
//        MessageOutput messageOut = MessageCenter.getUserMessageOut(player.getUsername());
//
//        int luck = rand.nextInt(20);
//        if(luck == 1){
//            messageOut.printToAll(name+ " ate plastic and die");
//            hp = 0;
//        }
//        else{
//            attack(player);
//        }
//    }
//
//    public void attack(Player p) {
//        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
//        messageOut.printToAll(name + " poisoned!");
//
//        int damage = atk;
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
