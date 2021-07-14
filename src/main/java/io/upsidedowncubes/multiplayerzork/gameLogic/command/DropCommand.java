package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DropCommand implements Command{

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public String getCommandName() {
        return "drop";
    }

    @Override
    public void execute(List<String> args, String username) {
        Item item = ItemFactory.getItem(args.get(1));
        Inventory inventory = new Inventory(username);

        if (item == null || inventory.hasNo( item ) ){
            MessageOutput.printToAll("No such item");
            return;
        }

        int amount = 1;
        if (args.size() == 3){
            try{
                amount = Integer.parseInt(args.get(2));
                if (amount <= 0){
                    MessageOutput.printToAll("Invalid Amount");
                    return;
                }

            }
            catch( NumberFormatException e ){
                MessageOutput.printToAll("Invalid Number format");
                return;
            }

        }

        if (inventory.lose(item, amount)){
            MessageOutput.printToAll("Dropped " + amount + " " + item.getName() + " successfully");
            entityUpdate.dropItem(username, item.getName(), amount);
        }
        else{
            MessageOutput.printToAll("Unable to drop " + amount + " " + item.getName() );
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
