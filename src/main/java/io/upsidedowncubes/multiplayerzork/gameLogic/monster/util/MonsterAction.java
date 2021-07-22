package io.upsidedowncubes.multiplayerzork.gameLogic.monster.util;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;


public class MonsterAction {

    public static void doAct(Monster m, Player p) {
        m.act(p);
    }

}
