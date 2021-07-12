package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Room;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttackCommand implements Command{

    @Autowired
    Game game;

    @Override
    public String getDescription() {
        return "This command is used for attacking a monster without any weapon";
    }

    @Override
    public void execute(List<String> args) {

        Room r = game.getMap().getCurrentRoom();
        Monster m = r.getMonster();
        if ( m == null ){
            MessageOutput.print("There's no monster in the room");
            return;
        }

        Player p = game.getPlayer();
        MessageOutput.print("You attacked the " + m.getName() + "!");

        int damage = p.attack(null);
        if (damage != -1){
            m.receiveDamage( damage );
            MessageOutput.print(m.getName() + " took " + damage + " damage");
        }

        if (m.isDead()){
            MessageOutput.print("You defeated " + m.getName());
            p.gainATK(1);
            r.removeMonster();
        }
        else{
            m.act();
        }
        p.check();

    }

    @Override
    public String getCommandName() {
        return "attack";
    }

    @Override
    public boolean callableNow() {
        return game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
