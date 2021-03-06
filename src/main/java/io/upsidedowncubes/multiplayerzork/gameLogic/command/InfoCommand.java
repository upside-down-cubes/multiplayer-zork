package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InfoCommand implements Command {

    @Override
    public String getCommandName() {
        return "info";
    }

    @Override
    public void execute(List<String> args, String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        Player p = new Player(username);
        //p.viewStatus();
        messageOut.printToUser(p.getBag().viewInventory());
        messageOut.printToUser(p.getCurrentRoom().lookAround());

    }


    @Override
    public boolean callableNow(String username) {
        // can only view info while playing
        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
