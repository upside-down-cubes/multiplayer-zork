package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Room;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TakeCommand implements Command{

    @Autowired
    Game game;

    @Override
    public String getCommandName() {
        return "take";
    }

    @Override
    public String getDescription() {
        return "This command is used for collecting Items found in rooms, usually followed by the item name";
    }

    @Override
    public void execute(List<String> args) {
        Item item = ItemFactory.getItem(args.get(1));
        if (item == null){
            // invalid item name
            MessageOutput.print("No such item");
            return;
        }

        Room r = game.getMap().getCurrentRoom();
        if ( ! r.canTake(item) ){
            // no such item in room / no item in room
            MessageOutput.print("No such item");
            return;
        }

        Inventory inventory = game.getInventory();
        if ( inventory.obtain(item) ){
            // if bag not full
            r.removeItem(); // remove item from room
            MessageOutput.print("Picked up " + item.getName());
        }
        else{
            MessageOutput.print("Can't pick up the item");
        }

    }


    @Override
    public boolean callableNow() {
        return game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
