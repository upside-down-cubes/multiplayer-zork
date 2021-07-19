package io.upsidedowncubes.multiplayerzork.gameLogic;

import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMap;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import java.util.List;
import java.util.Scanner;

@Component
public class Game {


    GameMap map;


    private boolean isPlaying = false;


    public void setGameState(boolean state){
        isPlaying = state;
    }
    public boolean gameInProcess(){
        return isPlaying;
    }


    public void setMap(GameMap map){
        this.map = map;
    }
    public GameMap getMap(){
        return map;
    }

}
