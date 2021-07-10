package upsidedowncubes.multiplayerzork.map;

import java.util.List;
import java.util.Set;

public abstract class GameMap {

    protected List<List<Room>> map;
    protected Location currentLoc;
    protected String mapName;

    public List<List<Room>> getMap() {
        return map;
    }

    public String getMapName(){
        return mapName;
    }

    public Location getCurrentLocation() {
        return currentLoc;
    }

    public Room getCurrentRoom() {
        return map.get( currentLoc.getRow() ).get( currentLoc.getCol() );
    }

    public Set<Direction> getAvailableExit() {
        Room currentRoom = getCurrentRoom();
        return currentRoom.exits;
    }

    public Direction move(Direction dir) {
        // check if can move there
        if (! getAvailableExit().contains(dir) ){
            return null;
        }

        // if can move, change currentLocation to be the location in that direction
        switch (dir){
            case N:
                currentLoc.goNorth();
                System.out.println("You proceeded to the North");
                break;
            case E:
                currentLoc.goEast();
                System.out.println("You proceeded to the East");
                break;
            case W:
                currentLoc.goWest();
                System.out.println("You proceeded to the West");
                break;
            case S:
                currentLoc.goSouth();
                System.out.println("You proceeded to the South");
                break;
        }
        return dir;
    }

    public abstract String getMapObjective();

    public abstract boolean objectiveSuccess();

}
