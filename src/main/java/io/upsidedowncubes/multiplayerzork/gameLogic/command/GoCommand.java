package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Direction;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoCommand implements Command{

    @Autowired
    Game game;

    @Override
    public String getCommandName() {
        return "go";
    }

    @Override
    public String getDescription() {
        return "This command is used for traversing the map, usually followed by North, East, South, West";
    }

    @Override
    public void execute(List<String> args) {
        Direction d = stringToDirection(args.get(1));
        if (d == null){
            MessageOutput.print("Invalid Direction");
            return;
        }
        Direction dir = game.getMap().move(d);
        if (dir == null){
            MessageOutput.print("It seems like you could not proceed in that direction");
        }
        else{
            if ( ! game.getPlayer().isFullHP())
                game.getPlayer().gainHP(1);
            game.getMap().getCurrentRoom().lookAround();
        }

    }



    public Direction stringToDirection(String str){
        if (str.equalsIgnoreCase("north") || str.equalsIgnoreCase("n") ){
            return Direction.N;
        }
        else if (str.equalsIgnoreCase("east") || str.equalsIgnoreCase("e") ){
            return Direction.E;
        }
        else if (str.equalsIgnoreCase("west") || str.equalsIgnoreCase("w") ){
            return Direction.W;
        }
        else if (str.equalsIgnoreCase("south") || str.equalsIgnoreCase("s") ){
            return Direction.S;
        }
        return null;
    }

    @Override
    public boolean callableNow() {
        // can only call once the game is in process
        return game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 1;
    }
}