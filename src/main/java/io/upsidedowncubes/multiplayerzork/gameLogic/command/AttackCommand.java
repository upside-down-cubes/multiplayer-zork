package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Location;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Room;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.MonsterAction;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.EntityUpdate;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class AttackCommand implements Command, Terminator{

    private boolean quit;

    @Autowired
    EntityUpdate entityUpdate;

    @Override
    public void execute(List<String> args, String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        quit = false;
        boolean useItem = false;

        Player p = new Player(username);

        Room r = p.getCurrentRoom();
        Monster m = r.getMonster();
        if ( m == null ){
            messageOut.printToUser("There's no monster in the room");
            return;
        }

        //=====================
        // with item
        Weapon wp = null;
        Item item = null;
        if (args.size() > 1){
            if (! args.get(1).equals("with")){
                messageOut.printToUser("Invalid command");
                return;
            }

            Inventory inventory = p.getBag();
            item = ItemFactory.getItem(args.get(2));

            if (item == null || inventory.hasNo( item ) ){
                messageOut.printToUser("No such item");
                return;
            }
            if (! (item instanceof Weapon) ){
                messageOut.printToUser("This item is not a Weapon");
                return;
            }
            wp = (Weapon) item;
            useItem = true;
        }

        //=====================

        if (useItem){
            messageOut.printToAll("[ " + username + " ] attacked the " + m.getName() + " with a " + item.getName() + "!");
        }
        else{
            messageOut.printToAll("[ " + username + " ] attacked the " + m.getName() + "!");
        }

        int damage = p.attack(wp);
        if (damage != -1){
            messageOut.printToAll( m.getName() + " took " + m.receiveDamage( damage ) + " damage!");
        }

        if (m.isDead()){
            messageOut.printToAll("[ " + username + " ] defeated " + m.getName());

            int monsterExp;

            Set<String> userInSession = OurWebSocketHandler.getAllUsersInSameSession(username);
            for (String name : userInSession){
                Location p2 = new Location(name);
                if( p2.equals( p.getCurrentLoc() ) ){
                    monsterExp = m.giveExp();
                    messageOut.printToAll("[ " + name + " ] gained " + monsterExp + " EXP!");
                    if ( entityUpdate.updateExp(name, monsterExp) ){
                        messageOut.printToAll("[ " + name + " ] leveled up!");
                        entityUpdate.updateAtk(name, 1);
                    }
                }

            }

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
        if (quit){
            messageOut.printToUser("You have fallen...");
            messageOut.printToOthers("[ " + username + " ] has fallen...");
            entityUpdate.setExp(username, 0);

            entityUpdate.setHp(username, p.getMaxHP() );
            entityUpdate.dropAllItems(username);
            messageOut.printToUser("You lost all your belonging...");
        }

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
