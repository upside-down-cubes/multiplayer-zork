package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

/*
* This more you attack this monster the more powerful it gets
* */
public class DragonMonster extends Monster{

    public DragonMonster(){
        super();
        maxHP = 300;
        hp = maxHP;
        atk = 3;
        name = "Dragon from hell";
    }

    @Override
    public void act(Player player) {
        System.out.println("DEBUG LOG: monster acts");
        attack( player );
    }

    private int attackIncrease(){
       return (maxHP-hp)-170;
    }
    // it attacks it increase with depending on how mush health it lost
    public void attack( Player p) {
        System.out.println("DEBUG LOG: monster attacks");
        MessageOutput.printToAll(name + " attacked!");
        atk = attackIncrease();
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
