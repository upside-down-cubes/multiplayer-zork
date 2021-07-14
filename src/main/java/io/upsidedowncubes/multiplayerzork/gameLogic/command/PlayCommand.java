package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMap;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMapFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayCommand implements Command {

    @Override
    public String getCommandName() {
        return "play";
    }


    @Override
    public void execute(List<String> args, String username) {
        Game game = OurWebSocketHandler.getGameByUser(username);

        // TODO: fix map
        GameMap chosenMap = GameMapFactory.getMap(args.get(1));
        if (chosenMap == null){
            MessageOutput.printToAll("No such map");
            return;
        }
        game.setMap( chosenMap );

        MessageOutput.printToAll("(Entered Game mode)\n");
        game.setGameState(true);

        String[] msg = new String[]{
                "You entered the region called " + chosenMap.getMapName(),
                "You arrived into a room you might not recognize...",
                //"Your objective is: " + chosenMap.getMapObjective(),
                ""
        };
        MessageOutput.printToAll(msg);
        Player p = new Player(username);
        p.getCurrentRoom().lookAround();

    }


    @Override
    public boolean callableNow(String username) {
        // if game is in process, can't start the game again
        return ! OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 1;
    }

}
