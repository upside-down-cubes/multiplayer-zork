package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.database.EntityUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RickRoller implements Item, Targetable {

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public String getName() {
        return "rickroller";
    }

    @Override
    public int getItemID() {
        return 666;
    }

    @Override
    public void useOn(String user_username, String target_username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(user_username);

        messageOut.printToUser("You used a " + getName() + " on [ " + target_username + " ]");
        entityUpdate.dropItem(user_username, getName(), 1);

    }
}
