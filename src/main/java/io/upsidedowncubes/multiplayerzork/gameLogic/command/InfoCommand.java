package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMap;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InfoCommand implements Command{

    @Autowired
    Game game;

    @Override
    public String getCommandName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "This command shows the current status of related to the game";
    }

    @Override
    public void execute(List<String> args) {

        game.getPlayer().viewStatus();
        game.getInventory().viewInventory();

        // for testing / debugging only
        GameMap m = game.getMap();
        Location l = m.getCurrentLocation();
        System.out.printf("LOG: Current Location =  Row %d, Col %d \n", l.getRow(), l.getCol());

        game.getMap().getCurrentRoom().lookAround();

    }


    @Override
    public boolean callableNow() {
        // can only view info while playing
        return game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
