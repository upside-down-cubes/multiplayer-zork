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
    public void use(String username) {

        MessageOutput.printToAll("Used Dummy item on self");
        Player p = new Player(username);
        if (p.isFullHP()){
            MessageOutput.printToAll("Fuck you, my health is full");
            return;
        }
        entityUpdate.updateHp(username, p.gainHP(50));

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
