package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;


public class DummyMonster extends Monster{

    public DummyMonster(){
        super();
        maxHP = 20;
        hp = maxHP;
        atk = 3;
        name = "<Generic Monster>";
        id = 0;
    }

    @Override
    public void act(Player player) {
        attack( player );
    }

    public void attack( Player p) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(p.getUsername());

        messageOut.printToUser(name + " attacked!");

        int damage = atk;

        int rng = rand.nextInt(3);
        if (rng == 1){
            damage--;
        }
        else if (rng == 2){
            damage++;
        }

        p.loseHP( damage );
        messageOut.printToUser("You took " + damage + " damage");

    }

    public void defend() {
        System.out.println("DEBUG LOG: monster defends");
    }

    public void skill() {
        System.out.println("DEBUG LOG: monster uses a skill");
    }
}
