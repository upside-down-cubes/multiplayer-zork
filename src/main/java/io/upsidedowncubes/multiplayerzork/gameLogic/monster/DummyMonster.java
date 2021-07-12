package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class DummyMonster extends Monster{

    @Autowired
    Game game;

    public DummyMonster(){
        super();
        maxHP = 20;
        hp = maxHP;
        atk = 3;
        name = "<Generic Monster>";
    }

    @Override
    public void act() {
        System.out.println("DEBUG LOG: monster acts");
        attack();
    }

    public void attack() {
        System.out.println("DEBUG LOG: monster attacks");
        MessageOutput.print(name + " attacked!");

        Player p = game.getPlayer();

        int damage = atk;

        int rng = rand.nextInt(3);
        if (rng == 1){
            damage--;
        }
        else if (rng == 2){
            damage++;
        }

        p.loseHP( damage );
        MessageOutput.print("You took " + damage + " damage");

    }

    public void defend() {
        System.out.println("DEBUG LOG: monster defends");
    }

    public void skill() {
        System.out.println("DEBUG LOG: monster uses a skill");
    }
}
