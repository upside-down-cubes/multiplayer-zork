package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Room;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TakeCommand implements Command{

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public String getCommandName() {
        return "take";
    }

    @Override
    public void execute(List<String> args, String username) {
        Game game = OurWebSocketHandler.getGameByUser(username);
        Item item = ItemFactory.getItem(args.get(1));
        if (item == null){
            // invalid item name
            MessageOutput.printToAll("No such item");
            return;
        }

        Room r = game.getMap().getCurrentRoom();
        if ( ! r.canTake(item) ){
            // no such item in room / no item in room
            MessageOutput.printToAll("No such item");
            return;
        }

        Inventory inventory = game.getInventory();
        if ( inventory.obtain(item) ){
            // if bag not full
            r.removeItem(); // remove item from room
            MessageOutput.printToAll("Picked up " + item.getName());
            entityUpdate.takeItem(username, item.getName(), 1);
        }
        else{
            MessageOutput.printToAll("Can't pick up the item");
        }

    }


    @Override
    public boolean callableNow(String username) {
        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
