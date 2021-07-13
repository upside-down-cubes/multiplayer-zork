package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.List;

@Component
public class ExitCommand implements Command{

    @Autowired
    Game game;

    @Autowired
    private ApplicationContext ac;

    @Override
    public void execute(List<String> args) {
        MessageOutput.print("Thanks for playing!");
        SpringApplication.exit(ac);
        System.exit(0);
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public boolean callableNow() {
        return ! game.gameInProcess();
    }

    @Override
    public int requiredArgs() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "This command is used for terminating the game";
    }
}
