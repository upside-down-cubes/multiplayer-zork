package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DummyConsumable implements Item, Consumable{

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public boolean use(String username) {

        MessageOutput.printToUser("Used Dummy item on self");
        Player p = new Player(username);
        if (p.isFullHP()){
            MessageOutput.printToUser("Fuck you, my health is full");
            return false;
        }
        entityUpdate.updateHp(username, p.gainHP(50));
        entityUpdate.dropItem( username, getName(), 1 );
        return false;
    }

    @Override
    public String getName() {
        return "dummy_con";
    }

    @Override
    public int getItemID() {
        return -1;
    }
}
