package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.ContextAwareClass;

public class Location {

    private static final PlayerRepository PLAYER_REPOSITORY = (PlayerRepository) ContextAwareClass
            .getApplicationContext().getBean("playerRepository");

    private int row;
    private int col;

    public Location(int row, int col){
        if (row < 0 || col < 0){
            this.row = 0;
            this.col = 0;
        }
        else{
            this.row = row;
            this.col = col;
        }

    }

    public Location(String username){
        PlayerEntity p = PLAYER_REPOSITORY.findByUsername(username);
        //this(p.getRow(), p.getCol());
    }

    public void goNorth(){
        row--;
    }

    public void goSouth(){
        row++;
    }

    public void goEast(){
        col--;
    }

    public void goWest(){
        col++;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
