package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.PlayerRepositoryHelper;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Potion implements Item, Consumable, Targetable{

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void use(String username) {
        Player p = new Player(username);
        if (p.isFullHP()){
            MessageOutput.printToUser("Your health is full, you can't use the potion");
            return;
        }
        MessageOutput.printToUser("You used a potion");
        entityUpdate.updateHp(username, p.gainHP(20));
        entityUpdate.dropItem( username, getName(), 1 );

    }

    @Override
    public void useOn(String user_username, String target_username) {
        MessageOutput.setSenderReceiver(user_username, target_username);
        Player p = new Player(target_username);
        if (p.isFullHP()){
            MessageOutput.printToUser("[ " + target_username + " ] 's health is full, you can't use the potion");
            return;
        }
        int gainedHp = p.gainHP(20);

        MessageOutput.printToUser("You used a potion on [" + target_username + " ]");
        MessageOutput.printToUser("They gained " + gainedHp + " HP");
        MessageOutput.printToDM( "[ " + user_username + " ] used a potion on you" );
        MessageOutput.printToDM( "You gained " + gainedHp + " HP" );
        MessageOutput.printToOthers("[ " + user_username + " ] used a potion on [" + target_username + " ]");

        entityUpdate.updateHp(target_username, gainedHp);
        entityUpdate.dropItem( user_username, getName(), 1 );

    }

    @Override
    public String getName() {
        return "potion";
    }

    @Override
    public int getItemID() {
        return 1;
    }


}
