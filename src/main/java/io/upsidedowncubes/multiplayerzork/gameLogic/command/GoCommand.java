package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.map.Direction;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Location;
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
        Player p = new Player(username);

        Direction d = stringToDirection(args.get(1));
        if (d == null){
            MessageOutput.printToUser("Invalid Direction");
            return;
        }

        if (p.getCurrentRoom().getMonster() != null){
            MessageOutput.printToUser("Can't escape from the Monster! Must deal with it somehow...");
            return;
        }

        Direction dir = p.move(d);
        if (dir == null){
            MessageOutput.printToUser("It seems like you could not proceed in that direction");
        }
        else{
            if ( ! p.isFullHP()){
                p.gainHP(1);
                entityUpdate.updateHp(username, 1);
            }
            Location loc = p.getCurrentLoc();
            entityUpdate.updateLoc(username, loc.getRow(), loc.getCol());

            //p.getCurrentRoom().lookAround();
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