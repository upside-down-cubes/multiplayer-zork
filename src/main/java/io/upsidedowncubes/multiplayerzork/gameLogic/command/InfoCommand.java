package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMap;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Location;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InfoCommand implements Command{

    @Override
    public String getCommandName() {
        return "info";
    }

    @Override
    public void execute(List<String> args, String username) {
        Game game = OurWebSocketHandler.getGameByUser(username);
        Player p = new Player(username);
        p.viewStatus();
        p.getBag().viewInventory();

        // for testing / debugging only
        // TODO: display available exits
        GameMap m = game.getMap();
        Location l = m.getCurrentLocation();


        game.getMap().getCurrentRoom().lookAround();

    }


    @Override
    public boolean callableNow(String username) {
        // can only view info while playing
        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
