package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuitCommand implements Command {


    @Override
    public String getCommandName()  {
        return "quit";
    }

    // TODO: change how this quit thing works
    @Override
    public void execute(List<String> args, String username) {
        Game game = OurWebSocketHandler.getGameByUser(username);
        // Game.isPlaying = false
        game.setGameState(false);
        MessageOutput.printToAll("(Returned to Menu mode)");
    }

    @Override
    public boolean callableNow(String username) {
        // can call if game is in process
        // will go back to menu
        return OurWebSocketHandler.getGameByUser(username).gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
