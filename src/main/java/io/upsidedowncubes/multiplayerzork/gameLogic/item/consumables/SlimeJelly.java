package io.upsidedowncubes.multiplayerzork.gameLogic.item.consumables;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Consumable;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SlimeJelly implements Item, Consumable {

    // drop from slimes

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void use(String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        Player p = new Player(username);
        if (p.isFullHP()){
            messageOut.printToUser("Your health is full, you can't use the " + getName());
            return;
        }
        messageOut.printToUser("You used " + getName());
        entityUpdate.updateHp(username, p.gainHP(5));
        entityUpdate.dropItem( username, getName(), 1 );

    }

    @Override
    public String getName() {
        return "slime_jelly";
    }

    @Override
    public int getItemID() {
        return 3;
    }


}
