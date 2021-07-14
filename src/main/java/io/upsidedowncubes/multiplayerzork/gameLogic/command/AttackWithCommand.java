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
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttackWithCommand implements Command, Terminator{

    private boolean quit;

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public String getCommandName() {
        return "attack with";
    }

    @Override
    public void execute(List<String> args, String username) {
        quit = false;

        Player p = new Player(username);

        Room r = p.getCurrentRoom();
        Monster m = r.getMonster();
        if ( m == null ){
            //TODO: edit message (to all and to others)
            MessageOutput.printToAll("There's no monster in the room");
            return;
        }


        Inventory inventory = p.getBag();
        Item item = ItemFactory.getItem(args.get(1));

        if (item == null || inventory.hasNo( item ) ){
            //TODO: edit message (to all and to others)
            MessageOutput.printToAll("No such item");
            return;
        }
        if (! (item instanceof Weapon) ){
            //TODO: edit message (to all and to others)
            MessageOutput.printToAll("This item is not a Weapon");
            return;
        }
        Weapon wp = (Weapon) item;


        //TODO: edit message (to all and to others)
        MessageOutput.printToAll("You attacked the " + m.getName() + "!");

        int damage = p.attack(wp);
        if (damage != -1){
            m.receiveDamage( damage );
            //TODO: edit message (to all and to others)
            MessageOutput.printToAll(m.getName() + " took " + damage + " damage");
        }

        if (m.isDead()){
            //TODO: edit message (to all and to others)
            MessageOutput.printToAll("You defeated " + m.getName());
            entityUpdate.updateAtk(username, 1);
            r.removeMonster();
        }
        else{
            int hp_before = p.getHp();
            MonsterAction.doAct(m, p);
            int hp_diff = hp_before - p.getHp() ;
            if (hp_diff != 0){
                entityUpdate.updateHp(username, hp_diff);
            }
        }
        quit = p.isDead();

    }

    @Override
    public boolean callableNow(String username) {
        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 1;
    }

    @Override
    public boolean willTerminate() {
        return quit;
    }
}
