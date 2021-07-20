package io.upsidedowncubes.multiplayerzork.gameLogic.monster.util;


import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.List;
import java.util.Random;

public interface Monster {

    // getter setter
    int getID();
    int getHP();
    int getMaxHP();
    int getAtk();
    int giveExp();
    String getName();
    int receiveDamage(int amount);
    boolean isDead();

    // actions
    void act(Player p);

}
