package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;



/*
* This monster has low health but you can Weather gain or lose health or ...
* */
public class PoisonFrogMonster extends Monster{

    public PoisonFrogMonster(){
        super();
        maxHP = 2;
        hp = maxHP;
        atk = 30;
        name = "Poison Frog";
    }

    @Override
    public void act(Player player) {
        System.out.println("DEBUG LOG: monster acts");
        int luck = rand.nextInt(3);
        if(luck == 1){
            poison( player );
        }
        else{
            MessageOutput.printToUser("You got lucky");
            player.gainHP(30);
        }
    }

    public void poison(Player p) {
        System.out.println("DEBUG LOG: monster attacks");
        MessageOutput.printToAll(name + " got poisoned!");

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
