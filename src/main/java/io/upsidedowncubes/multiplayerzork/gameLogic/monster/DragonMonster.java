package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

/*
* This more you attack this monster the more powerful it gets
* */
public class DragonMonster extends Monster{

    public DragonMonster(){
        super();
        maxHP = 100;
        hp = maxHP;
        atk = 3;
        name = "Dragon from hell";

        id = 6;
    }

    @Override
    public void act(Player player) {
        attack( player );
    }

    private int attackIncrease(){
        int healthDiff = maxHP - hp;
        if (healthDiff > 0){
            return atk + healthDiff;
        }
        return atk + rand.nextInt(5);
    }

    // its attack increase depending on how mush health it lost
    public void attack( Player p) {

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
