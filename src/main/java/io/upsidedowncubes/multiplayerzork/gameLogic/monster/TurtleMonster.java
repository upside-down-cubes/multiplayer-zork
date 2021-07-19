package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;


/*
* This monster has low health but you can Weather gain or lose health or ...
* */
public class TurtleMonster extends Monster{

    public TurtleMonster(){
        super();
        maxHP = 900;
        hp = maxHP;
        atk = 1;
        name = "Turtle";
    }

    @Override
    public void act(Player player) {
        System.out.println("DEBUG LOG: monster acts");
        int luck = rand.nextInt(20);
        if(luck == 1){
            MessageOutput.printToAll(name+ " ate plastic and die");
            hp = 0;
        }
        else{
            attack(player);
        }
    }

    public void attack(Player p) {
        System.out.println("DEBUG LOG: monster attacks");
        MessageOutput.printToAll(name + " poisoned!");

        int damage = atk;

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