package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuitCommand implements Command {

    @Autowired
    Game game;

    @Override
    public String getCommandName()  {
        return "quit";
    }

    @Override
    public String getDescription() {
        return "This command lets the player leave the game into the menu mode";
    }

    @Override
    public void execute(List<String> args) {
        // Game.isPlaying = false
        game.setGameState(false);
        MessageOutput.print("(Returned to Menu mode)");
    }

    @Override
    public boolean callableNow() {
        // can call if game is in process
        // will go back to menu
        return game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
