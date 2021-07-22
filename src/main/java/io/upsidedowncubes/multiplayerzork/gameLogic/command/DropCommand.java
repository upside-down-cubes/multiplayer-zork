package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DropCommand implements Command {

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public String getCommandName() {
        return "drop";
    }

    @Override
    public void execute(List<String> args, String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        Item item = ItemFactory.getItem(args.get(1));
        Player p = new Player(username);
        Inventory inventory = p.getBag();

        if (item == null || inventory.hasNo(item)) {
            messageOut.printToUser("No such item");
            return;
        }

        int amount = 1;
        if (args.size() == 3) {
            try {
                amount = Integer.parseInt(args.get(2));
                if (amount <= 0) {
                    messageOut.printToUser("Invalid Amount");
                    return;
                }

            } catch (NumberFormatException e) {
                messageOut.printToUser("Invalid Number format");
                return;
            }

        }

        if (inventory.lose(item, amount)) {
            messageOut.printToAll("[ " + username + " ] dropped " + amount + " " + item.getName() + " successfully");
            entityUpdate.dropItem(username, item.getName(), amount);

            p.getCurrentRoom().addItem(item);
        } else {
            messageOut.printToUser("Unable to drop " + amount + " " + item.getName());
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
