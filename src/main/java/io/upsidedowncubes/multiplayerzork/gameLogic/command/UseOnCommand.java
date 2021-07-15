package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.*;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.ContextAwareClass;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UseOnCommand implements Command{

    private static final PlayerRepository PLAYER_REPOSITORY = (PlayerRepository) ContextAwareClass
            .getApplicationContext().getBean("playerRepository");

    @Override
    public String getCommandName() {
        return "use on";
    }


    @Override
    public void execute(List<String> args, String username) {
        // How to use --> [ "/use on" , "player_a" , "use" , "potion" ]

        if (! args.get(2).equals("use")){
            MessageOutput.printToUser("Invalid command");
            return;
        }

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

        PlayerEntity p2 = PLAYER_REPOSITORY.findByUsername(args.get(1));
        if (p2 == null){
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
        return 3;
    }
}
