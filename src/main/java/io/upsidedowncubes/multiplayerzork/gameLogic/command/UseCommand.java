package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.*;
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

        Item item = ItemFactory.getItem(args.get(1));
        Inventory inventory = new Inventory(username);
        if (item == null || inventory.hasNo( item ) ){
            MessageOutput.printToUser("No such item");
            return;
        }

        if ( (item instanceof Targetable) ){
            MessageOutput.printToUser("This item Can't be used on self (HINT: check out \"use on\" command)");
            return;
        }
        if (! (item instanceof Consumable) ){
            MessageOutput.printToUser("This item is not a Consumable");
            return;
        }

        ((Consumable) item).use(username);
        // the Consumable.use will deal with database

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
