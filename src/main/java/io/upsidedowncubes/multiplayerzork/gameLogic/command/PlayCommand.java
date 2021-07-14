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

    // TODO: fix how player will select the map
    @Override
    public void execute(List<String> args, String username) {
        Game game = OurWebSocketHandler.getGameByUser(username);

        GameMap chosenMap = GameMapFactory.getMap(args.get(1));
        if (chosenMap == null){
            MessageOutput.printToAll("No such map");
            return;
        }
        game.setMap( chosenMap );

        MessageOutput.printToAll("(Entered Game mode)\n");
        game.setGameState(true);
        game.setPlayer( new Player() );

        String[] msg = new String[]{
                "You entered the world of " + chosenMap.getMapName(),
                "You arrived into a room you have never seen...",
                "Your objective is: " + chosenMap.getMapObjective(),
                ""
        };
        MessageOutput.printToAll(msg);
        game.getMap().getCurrentRoom().lookAround();

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
