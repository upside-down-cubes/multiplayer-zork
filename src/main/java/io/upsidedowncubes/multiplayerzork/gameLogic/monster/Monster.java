package io.upsidedowncubes.multiplayerzork.gameLogic.monster;


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
    String getName();

    void receiveDamage(int amount);

    boolean isDead();

    // actions
    void act(Player p);

}
