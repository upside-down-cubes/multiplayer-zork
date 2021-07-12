package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DropCommand implements Command{

    @Autowired
    Game game;
    @Override
    public String getCommandName() {
        return "drop";
    }

    @Override
    public String getDescription() {
        return "This command is for dropping unwanted item, usually followed by an item name, might be followed by amount";
    }

    @Override
    public void execute(List<String> args) {
        Item item = ItemFactory.getItem(args.get(1));
        Inventory inventory = game.getInventory();
        if (item == null || inventory.hasNo( item ) ){
            MessageOutput.print("No such item");
            return;
        }

        int amount = 1;
        if (args.size() == 3){
            try{
                amount = Integer.parseInt(args.get(2));
                if (amount <= 0){
                    MessageOutput.print("Invalid Amount");
                    return;
                }

            }
            catch( NumberFormatException e ){
                MessageOutput.print("Invalid Number format");
                return;
            }

        }

        if (inventory.lose(item, amount)){
            MessageOutput.print("Dropped " + amount + " " + item.getName() + " successfully");
        }
        else{
            MessageOutput.print("Unable to drop " + amount + " " + item.getName() );
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
