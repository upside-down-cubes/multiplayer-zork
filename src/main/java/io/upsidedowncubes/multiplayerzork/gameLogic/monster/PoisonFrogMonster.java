package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
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
        id = 7;
    }

    @Override
    public void act(Player player) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(player.getUsername());

        int luck = rand.nextInt(3);
        if(luck == 1){
            poison( player );
        }
        else{
            messageOut.printToUser("You got lucky");
            player.gainHP(30);
        }
    }

    public void poison(Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());
        messageOut.printToAll(name + " poisoned!");

        int damage = atk;

        p.loseHP( damage );
        messageOut.printToAll("You took " + damage + " damage");

    }

    public void defend() {
        System.out.println("DEBUG LOG: monster defends");
    }

    public void skill() {
        System.out.println("DEBUG LOG: monster uses a skill");
    }
}
