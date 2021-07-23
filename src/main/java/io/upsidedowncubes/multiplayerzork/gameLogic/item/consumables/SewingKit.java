package io.upsidedowncubes.multiplayerzork.gameLogic.item.consumables;

import io.upsidedowncubes.multiplayerzork.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Consumable;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SewingKit implements Item, Consumable {

    private static final int CAPACITY_INCR = 2;

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void use(String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        messageOut.printToUser("You used a " + getName());
        messageOut.printToUser("Your Bag capacity is increased by " + CAPACITY_INCR);
        entityUpdate.updateBagCap(username, CAPACITY_INCR);
        entityUpdate.dropItem(username, getName(), 1);

    }


    @Override
    public String getName() {
        return "sewing_kit";
    }

    @Override
    public int getItemID() {
        return 12;
    }


}
