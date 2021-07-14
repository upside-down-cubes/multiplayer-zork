package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.map.Room;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.MonsterAction;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttackCommand implements Command, Terminator{

    private boolean quit;

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void execute(List<String> args, String username) {
        quit = false;

        Player p = new Player(username);

        Room r = p.getCurrentRoom();
        Monster m = r.getMonster();
        if ( m == null ){
            MessageOutput.printToUser("There's no monster in the room");
            return;
        }

        MessageOutput.printToAll("[ " + username + " ] attacked the " + m.getName() + "!");

        int damage = p.attack(null);
        if (damage != -1){
            m.receiveDamage( damage );
            MessageOutput.printToAll(m.getName() + " took " + damage + " damage");
        }

        if (m.isDead()){
            MessageOutput.printToAll("[ " + username + " ] defeated " + m.getName());
            entityUpdate.updateAtk(username, 1);
            r.removeMonster();
        }
        else{
            int hp_before = p.getHp();
            MonsterAction.doAct(m, p);
            int hp_diff = p.getHp() - hp_before ;
            if (hp_diff != 0){
                entityUpdate.updateHp(username, hp_diff);
            }
        }
        quit = p.isDead();

    }

    @Override
    public String getCommandName() {
        return "attack";
    }

    @Override
    public boolean callableNow(String username) {
        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }

    @Override
    public boolean willTerminate() {
        return quit;
    }
}
