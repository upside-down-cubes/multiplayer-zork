package io.upsidedowncubes.multiplayerzork.gameLogic.item.weapon;

import io.upsidedowncubes.multiplayerzork.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TrainingSword implements Item, Weapon {

    @Autowired
    EntityUpdate entityUpdate;

    Random rand = new Random();

    @Override
    public String getName() {
        return "training_sword";
    }

    @Override
    public int getItemID() {
        return 11;
    }

    @Override
    public int getAttackStat(Player p) {
        return 0;
    }

    @Override
    public void passiveEffect(Player p) {

    }

    @Override
    public void activeEffect(Player p) {
        if (rand.nextInt(10) < 3) {
            String username = p.getUsername();
            MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

            messageOut.printToAll("[ " + username + " ]'s training_sword helped them practice!");
            messageOut.printToAll("[ " + username + " ] gained an extra EXP");
            if (entityUpdate.updateExp(username, 1)) {
                messageOut.printToAll("[ " + username + " ] leveled up!");
                entityUpdate.updateAtk(username, 1);
            }
        }
    }
}
