package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GiantBerry implements Item, Consumable{

    private static final int MAXHP_INCR = 3;

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void use(String username) {
        Player p = new Player(username);

        MessageOutput.printToUser("You used a " + getName());
        p.gainMaxHP(MAXHP_INCR);
        entityUpdate.updateMaxHp(username, MAXHP_INCR);
        entityUpdate.dropItem( username, getName(), 1 );

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