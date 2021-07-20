package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;

public interface Weapon {

    int getAttackStat(Player p);

    void passiveEffect(Player p);
    void activeEffect(Player p);

}
