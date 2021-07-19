package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

/*
* The more he attacks the more tiered he become, but gains his power a attack harder again
* */
public class BoxerMonster extends Monster{

    int amountOfAttacks;
    public BoxerMonster(){
        super();
        maxHP = 50;
        hp = maxHP;
        atk = 13;
        name = "Mike Tyson";
        amountOfAttacks = 0;

        id = 4;
    }

    @Override
    public void act(Player player) {

        int luck = rand.nextInt(4);
        if (luck <= 1){
            MessageOutput.printToUser(name + "'s attack misses");
        }
        else{
            attack( player );
        }
        amountOfAttacks++;
        attackDecrease();
    }

    private void attackDecrease(){
        atk -= amountOfAttacks;
        if (atk < 1){
            atk = 13;
            amountOfAttacks = 1;
        }

    }

    public void attack( Player p) {

        MessageOutput.printToAll(name + " attacked!");

        int damage = atk;
        p.loseHP( damage );
        MessageOutput.printToUser("You took " + damage + " damage");

    }

}
