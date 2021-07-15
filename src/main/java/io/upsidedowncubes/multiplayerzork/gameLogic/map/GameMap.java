package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import java.util.List;

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
