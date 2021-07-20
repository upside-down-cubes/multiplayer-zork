package io.upsidedowncubes.multiplayerzork.gameLogic.item.consumables;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Consumable;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Targetable;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Potion implements Item, Consumable, Targetable {

    private final int HEAL_AMOUNT = 20;

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void use(String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        Player p = new Player(username);
        if (p.isFullHP()){
            messageOut.printToUser("Your health is full, you can't use the potion");
            return;
        }
        messageOut.printToUser("You used a potion");
        entityUpdate.updateHp(username, p.gainHP(HEAL_AMOUNT));
        entityUpdate.dropItem( username, getName(), 1 );

    }

    @Override
    public void useOn(String user_username, String target_username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(user_username);
        messageOut.setSenderReceiver(user_username, target_username);

        if (! OurWebSocketHandler.inSameSession(user_username, target_username) ){
            messageOut.printToUser("No such user in this session");
            return;
        }

        Player p = new Player(target_username);
        if (p.isFullHP()){
            messageOut.printToUser("[ " + target_username + " ] 's health is full, you can't use the potion");
            return;
        }

        int gainedHp;
        if (p.getHp() + HEAL_AMOUNT > p.getMaxHP()){
            gainedHp = p.getMaxHP() - p.getHp();
        }
        else{
            gainedHp = HEAL_AMOUNT;
        }

        messageOut.printToUser("You used a potion on [" + target_username + " ]");
        messageOut.printToUser("They gained " + gainedHp + " HP");
        messageOut.printToDM( "[ " + user_username + " ] used a potion on you" );
        messageOut.printToDM( "You gained " + gainedHp + " HP" );
        messageOut.printToOthers("[ " + user_username + " ] used a potion on [" + target_username + " ]");

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
