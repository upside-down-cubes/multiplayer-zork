package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Room;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.MonsterAction;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttackWithCommand implements Command{

    @Autowired
    Game game;

    @Autowired
    MonsterAction monsterAction;

    @Override
    public String getCommandName() {
        return "attack with";
    }

    @Override
    public String getDescription() {
        return "This command is used for attacking a monster with a weapon you have";
    }

    @Override
    public void execute(List<String> args) {

        Room r = game.getMap().getCurrentRoom();
        Monster m = r.getMonster();
        if ( m == null ){
            MessageOutput.print("There's no monster in the room");
            return;
        }

        Item item = ItemFactory.getItem(args.get(1));
        Inventory inventory = game.getInventory();
        if (item == null || inventory.hasNo( item ) ){
            MessageOutput.print("No such item");
            return;
        }
        if (! (item instanceof Weapon) ){
            MessageOutput.print("This item is not a Weapon");
            return;
        }
        Weapon wp = (Weapon) item;

        Player p = game.getPlayer();
        MessageOutput.print("You attacked the " + m.getName() + "!");

        int damage = p.attack(wp);
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
            monsterAction.doAct(m);
        }
        p.check();

    }



    @Override
    public boolean callableNow() {
        return game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 1;
    }
}
