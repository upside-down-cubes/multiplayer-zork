package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

public class Slime extends Monster{

    public Slime(){
        super();
        maxHP = 20;
        hp = maxHP;
        atk = 2;
        name = "Slime";
        id = 1;

    }

    @Override
    public void act(Player p) {

        int rng = rand.nextInt(10);
        if (rng == 0){
            MessageOutput.printToAll(name + " failed to attack...");
        }
        else if (rng <= 4){
            attack(p);
        }
        else{
            MessageOutput.printToAll(name + " performed a continuous attack!");
            attack(p);
            attack(p);
            attack(p);

        }

    }

    public void attack( Player p ){

        if (rand.nextInt(10) <= 2){
            MessageOutput.printToAll(name + "'s attack misses");
        }

        MessageOutput.printToAll(name + " attacked!");
        int damage = atk + rand.nextInt(2);

        p.loseHP( damage );
        MessageOutput.printToUser("You took " + damage + " damage");
        MessageOutput.printToOthers( p.getUsername() + " took " + damage + " damage");
    }


}
