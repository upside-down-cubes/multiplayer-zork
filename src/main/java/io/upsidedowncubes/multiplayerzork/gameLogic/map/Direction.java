package io.upsidedowncubes.multiplayerzork.gameLogic.map;

public enum Direction {
    N("NORTH"),
    E("EAST"),
    W("WEST"),
    S("SOUTH");

    String name;
    Direction(String str){
        this.name = str;
    }
}
