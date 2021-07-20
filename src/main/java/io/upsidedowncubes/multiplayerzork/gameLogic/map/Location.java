package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.PlayerRepositoryHelper;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;

public class Location {

    private int row;
    private int col;

    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Location(String username){
        PlayerEntity p = PlayerRepositoryHelper.getPlayerEntity(username);
        if (p.getRow() < 0 || p.getCol() < 0){
            Game game = OurWebSocketHandler.getGameByUser(username);
            Location loc = game.getMap().getStartingLoc();
            this.row = loc.getRow();
            this.col = loc.getCol();
            p.setCol(loc.getCol());
            p.setRow(loc.getRow());
            PlayerRepositoryHelper.save(p);
        }
        else{
            this.row = p.getRow();
            this.col = p.getCol();
        }
    }


    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        else if (! (o instanceof Location) ){
            return false;
        }
        return ((Location) o).getCol() == col && ((Location) o).getRow() == row;
    }

    public void setLoc(int row, int col){
        this.row = row;
        this.col = col;
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
