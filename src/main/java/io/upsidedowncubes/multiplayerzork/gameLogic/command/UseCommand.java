package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.*;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.PlayerRepositoryHelper;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UseCommand implements Command {

    @Override
    public String getCommandName() {
        return "use";
    }


    @Override
    public void execute(List<String> args, String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        // use on syntax --> use <item> on <player>
        if (args.size() > 2) {
            if (!args.get(2).equals("on")) {
                messageOut.printToUser("Invalid command");
                return;
            }
            useOn(args, username);
            return;
        }


        Item item = ItemFactory.getItem(args.get(1));
        Inventory inventory = new Inventory(username);
        if (item == null || inventory.hasNo(item)) {
            messageOut.printToUser("No such item");
            return;
        }


        if (!(item instanceof Consumable)) {
            if ((item instanceof Targetable)) {
                messageOut.printToUser("This item Can't be used on self (HINT: check out \"use on\" command)");
                return;
            }
            messageOut.printToUser("This item is not a Consumable");
            return;
        }

        ((Consumable) item).use(username);
        // the Consumable.use will deal with database

    }

    public void useOn(List<String> args, String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        // use on syntax --> use <item> on <player>
        Player p = new Player(username);

        Item item = ItemFactory.getItem(args.get(1));
        Inventory inventory = p.getBag();
        if (item == null || inventory.hasNo(item)) {
            messageOut.printToUser("No such item");
            return;
        }

        if (!(item instanceof Targetable)) {
            messageOut.printToUser("This item can't be used on other player");
            return;
        }

        if (!PlayerRepositoryHelper.userExists(args.get(3))) {
            messageOut.printToUser("No such player");
            return;
        }

        ((Targetable) item).useOn(username, args.get(3));
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
