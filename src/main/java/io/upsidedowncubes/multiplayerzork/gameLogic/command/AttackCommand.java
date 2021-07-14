package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
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
public class AttackCommand implements Command{

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void execute(List<String> args, String username) {
        Game game = OurWebSocketHandler.getGameByUser(username);

        Room r = game.getMap().getCurrentRoom();
        Monster m = r.getMonster();
        if ( m == null ){
            MessageOutput.printToAll("There's no monster in the room");
            return;
        }

        Player p = new Player(username);

        //TODO: edit message (to all and to others)
        MessageOutput.printToAll("You attacked the " + m.getName() + "!");

        int damage = p.attack(null);
        if (damage != -1){
            m.receiveDamage( damage );
            //TODO: edit message (to all and to others)
            MessageOutput.printToAll(m.getName() + " took " + damage + " damage");
        }

        if (m.isDead()){
            //TODO: edit how it will increase everyone's attack
            //TODO: edit message (to all and to others)
            MessageOutput.printToAll("You defeated " + m.getName());
            entityUpdate.updateAtk(username, 1);
            r.removeMonster();
        }
        else{
            MonsterAction.doAct(m, p);
        }
        p.check();

        //TODO: update player into

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
}
