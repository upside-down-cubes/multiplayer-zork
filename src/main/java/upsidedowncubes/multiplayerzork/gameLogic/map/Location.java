package upsidedowncubes.multiplayerzork.gameLogic.map;

public class Location {
    private int row;
    private int col;

    public Location(int row, int col){
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
