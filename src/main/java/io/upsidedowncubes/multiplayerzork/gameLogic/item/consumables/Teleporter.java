package io.upsidedowncubes.multiplayerzork.gameLogic.item.consumables;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Consumable;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Teleporter implements Item, Consumable {

    @Autowired
    EntityUpdate entityUpdate;


    @Override
    public String getName() {
        return "teleporter";
    }

    @Override
    public int getItemID() {
        return 10;
    }


    @Override
    public void use(String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        Player p = new Player(username);
        int[] mapLimit = p.getCurrentRoom().getGameMap().getRowColLimit();
        messageOut.printToUser("You have been teleported somewhere...");

        int newRow = new Random().nextInt(mapLimit[0]);
        int newCol = new Random().nextInt(mapLimit[1]);
        p.getCurrentLoc().setLoc( newRow, newCol );

        entityUpdate.updateLoc(username, newRow, newCol);
        entityUpdate.dropItem( username, getName(), 1 );

    }


}
