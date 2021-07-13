package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMap;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMapFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayCommand implements Command {

    @Autowired
    Game game;


    @Override
    public String getCommandName() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "This command is used for choosing a certain map to play";
    }

    @Override
    public void execute(List<String> args) {

        GameMap chosenMap = GameMapFactory.getMap(args.get(1));
        if (chosenMap == null){
            MessageOutput.print("No such map");
            return;
        }
        game.setMap( chosenMap );

        MessageOutput.print("(Entered Game mode)\n");
        game.setGameState(true);
        game.setPlayer( new Player() );

        String[] msg = new String[]{
                "You entered the world of " + chosenMap.getMapName(),
                "You arrived into a room you have never seen...",
                "Your objective is: " + chosenMap.getMapObjective(),
                ""
        };
        MessageOutput.print(msg);
        game.getMap().getCurrentRoom().lookAround();

    }


    @Override
    public boolean callableNow() {
        // if game is in process, can't start the game again
        return ! game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 1;
    }

}