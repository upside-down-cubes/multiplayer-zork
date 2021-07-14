package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Direction;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoCommand implements Command{

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public String getCommandName() {
        return "go";
    }

    @Override
    public void execute(List<String> args, String username) {
        Game game = OurWebSocketHandler.getGameByUser(username);
        Player p = new Player(username);

        Direction d = stringToDirection(args.get(1));
        if (d == null){
            // TODO: edit message
            MessageOutput.printToAll("Invalid Direction");
            return;
        }
        Direction dir = game.getMap().move(d);
        if (dir == null){
            // TODO: edit message
            MessageOutput.printToAll("It seems like you could not proceed in that direction");
        }
        else{
            if ( ! p.isFullHP()){
                p.gainHP(1);
                entityUpdate.updateHp(username, 1);
            }
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
    public boolean callableNow(String username) {
        // can only call once the game is in process
        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 1;
    }
}