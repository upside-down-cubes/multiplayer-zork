package io.upsidedowncubes.multiplayerzork.gameLogic;

import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMap;
import org.springframework.stereotype.Component;

@Component
public class Game {


    GameMap map;


    private boolean isPlaying = false;


    public void setGameState(boolean state) {
        isPlaying = state;
    }

    public boolean gameInProcess() {
        return isPlaying;
    }


    public void setMap(GameMap map) {
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }

}
