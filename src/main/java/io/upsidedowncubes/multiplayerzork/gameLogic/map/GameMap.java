package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.List;
import java.util.Set;

public abstract class GameMap {

    protected List<List<Room>> map;
    protected String mapName;

    public List<List<Room>> getMap() {
        return map;
    }

    public String getMapName(){
        return mapName;
    }

    public Room getRoom(int row , int col) {
        return map.get( row ).get( col );
    }

    public abstract Location getStartingLoc();

}
