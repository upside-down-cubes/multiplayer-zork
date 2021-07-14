package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuitCommand implements Command, Terminator {

    @Override
    public String getCommandName()  {
        return "quit";
    }

    // TODO: change how this quit thing works
    @Override
    public void execute(List<String> args, String username) {
        // Game.isPlaying = false
        MessageOutput.printToAll(username + " has quit the game.");
    }

    @Override
    public boolean callableNow(String username) {
        // can call if game is in process
        // will go back to menu
//        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
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
