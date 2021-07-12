package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Consumable;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UseCommand implements Command{

    @Autowired
    Game game;

    @Override
    public String getCommandName() {
        return "use";
    }

    @Override
    public String getDescription() {
        return "This command is for consuming an item in your inventory, usually followed by an item name";
    }

    @Override
    public void execute(List<String> args) {
        Item item = ItemFactory.getItem(args.get(1));
        Inventory inventory = game.getInventory();
        if (item == null || inventory.hasNo( item ) ){
            MessageOutput.print("No such item");
            return;
        }

        if (! (item instanceof Consumable) ){
            MessageOutput.print("This item is not a Consumable");
            return;
        }

        ((Consumable) item).use();

    }


    @Override
    public boolean callableNow() {
        return game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 1;
    }
}
