package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class MonsterAction {

    public static void doAct(Monster m, Player p){
        m.act( p );
    }

}
