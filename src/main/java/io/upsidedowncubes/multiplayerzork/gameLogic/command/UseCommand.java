package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Consumable;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
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
            MessageOutput.printToAll("No such item");
            return;
        }

        if (! (item instanceof Consumable) ){
            MessageOutput.printToAll("This item is not a Consumable");
            return;
        }

        ((Consumable) item).use(username);
        // the item.use will deal with database

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
