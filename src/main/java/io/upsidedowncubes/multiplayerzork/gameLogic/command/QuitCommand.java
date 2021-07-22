package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuitCommand implements Command, Terminator {

    @Override
    public String getCommandName() {
        return "quit";
    }

    @Override
    public void execute(List<String> args, String username) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        // Game.isPlaying = false
        messageOut.printToOthers("[" + username + " ] has quit the game.");
        messageOut.printToUser("You left the game session...");
    }

    @Override
    public boolean callableNow(String username) {
        // return OurWebSocketHandler.getGameByUser(username).gameInProcess();
        return true;
    }

    @Override
    public int requiredArgs() {
        return 0;
    }

    @Override
    public boolean willTerminate() {
        return true;
    }
}
