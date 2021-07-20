package io.upsidedowncubes.multiplayerzork.gameLogic.item.weapon;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Location;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Staff implements Item, Weapon {

    private int HEAL_AMOUNT = 5;

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public String getName() {
        return "staff";
    }

    @Override
    public int getItemID() {
        return 0;
    }

    @Override
    public int getAttackStat(Player p) {
        return 1;
    }

    @Override
    public void passiveEffect(Player p) {

    }

    @Override
    public void activeEffect(Player p) {
        String username = p.getUsername();
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        Set<String> userInSession = OurWebSocketHandler.getAllUsersInSameSession(username);
        for (String name : userInSession){
            Location p2 = new Location(name);
            if( p2.equals( p.getCurrentLoc() ) ){
                entityUpdate.updateHp(name, HEAL_AMOUNT);
            }
        }
        messageOut.printToAll("All players in the same room as [ " + username + " ] is healed by " + HEAL_AMOUNT );
    }
}
