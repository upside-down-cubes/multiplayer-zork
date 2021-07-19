package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.*;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.PlayerRepositoryHelper;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UseCommand implements Command{

    @Override
    public String getCommandName() {
        return "use";
    }


    @Override
    public void execute(List<String> args, String username) {

        if (args.size() > 2){
            if (! args.get(2).equals("on")){
                MessageOutput.printToUser("Invalid command");
                return;
            }
            useOn(args, username);
            return;
        }
        // move on syntax --> use <item> on <player>


        Item item = ItemFactory.getItem(args.get(1));
        Inventory inventory = new Inventory(username);
        if (item == null || inventory.hasNo( item ) ){
            MessageOutput.printToUser("No such item");
            return;
        }


        if (! (item instanceof Consumable) ){
            if ( (item instanceof Targetable) ){
                MessageOutput.printToUser("This item Can't be used on self (HINT: check out \"use on\" command)");
                return;
            }
            MessageOutput.printToUser("This item is not a Consumable");
            return;
        }

        ((Consumable) item).use(username);
        // the Consumable.use will deal with database

    }

    public void useOn(List<String> args, String username) {

        Player p = new Player(username);

        Item item = ItemFactory.getItem(args.get(3));
        Inventory inventory = p.getBag();
        if (item == null || inventory.hasNo( item ) ){
            MessageOutput.printToUser("No such item");
            return;
        }

        if (! (item instanceof Targetable) ){
            MessageOutput.printToUser("This item can't be used on other player");
            return;
        }

        if (! PlayerRepositoryHelper.userExists( args.get(1)) ){
            MessageOutput.printToUser("No such player");
            return;
        }

        ((Targetable) item).useOn(username, args.get(1));
        // the Targetable.useOn will deal with database
    }


    @Override
    public boolean callableNow(String username) {
        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 1;
    }
}
