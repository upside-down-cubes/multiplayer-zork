package io.upsidedowncubes.multiplayerzork.gameLogic.item.consumables;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Consumable;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GiantBerry implements Item, Consumable {

    private static final int MAXHP_INCR = 3;

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void use(String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        messageOut.printToUser("You used a " + getName());
        messageOut.printToUser("You MaxHP is increased by " + MAXHP_INCR);
        entityUpdate.updateMaxHp(username, MAXHP_INCR);
        entityUpdate.dropItem(username, getName(), 1);

    }


    @Override
    public String getName() {
        return "giant_berry";
    }

    @Override
    public int getItemID() {
        return 5;
    }


}
