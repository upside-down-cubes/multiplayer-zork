package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;


/*
* This monster has low health but you can Weather gain or lose health or ...
* */
public class CobraMonster extends Monster{

    public CobraMonster(){
        super();
        maxHP = 40;
        hp = maxHP;
        atk = 20;
        name = "Cobra";
        id = 5;
    }

    @Override
    public void act(Player player) {

        int luck = rand.nextInt(5);
        if(luck == 1){
            attack( player );
        }
        else{
            MessageOutput.printToUser(name + "'s attack misses'");
        }
    }

    public void attack(Player p) {

        MessageOutput.printToAll(name + " attack!");

        int damage = atk + rand.nextInt(10);

        p.loseHP( damage );
        MessageOutput.printToAll("You took " + damage + " damage");

    }

    public void defend() {
        System.out.println("DEBUG LOG: monster defends");
    }

    public void skill() {
        System.out.println("DEBUG LOG: monster uses a skill");
    }
}
