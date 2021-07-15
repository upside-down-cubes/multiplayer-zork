package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

public class Goblin extends Monster{

    public Goblin(){
        super();
        maxHP = 30;
        hp = maxHP;
        atk = 4;
        name = "Goblin";
        id = 2;
    }

    @Override
    public void act(Player p) {

        attack(p);

    }

    public void attack( Player p ){

        if (rand.nextInt(10) <= 2){
            MessageOutput.printToAll(name + "'s attack misses");
        }

        MessageOutput.printToAll(name + " attacked!");
        int damage = atk + rand.nextInt(4);

        p.loseHP( damage );
        MessageOutput.printToUser("You took " + damage + " damage");
        MessageOutput.printToOthers( p.getUsername() + " took " + damage + " damage");
    }


}
