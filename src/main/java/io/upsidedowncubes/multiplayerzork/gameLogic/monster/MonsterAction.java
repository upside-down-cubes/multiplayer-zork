package io.upsidedowncubes.multiplayerzork.gameLogic.monster;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonsterAction {

    @Autowired
    Game game;

    public void doAct(Monster m){
        m.act( game.getPlayer() );
    }

}
