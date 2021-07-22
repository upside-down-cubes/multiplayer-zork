package io.upsidedowncubes.multiplayerzork.gameLogic.monster.util;


import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;

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
