package upsidedowncubes.multiplayerzork.gameLogic.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExitCommand implements Command{

    @Autowired
    private ApplicationContext ac;

    @Override
    public void execute(List<String> args) {
        SpringApplication.exit(ac);
        System.exit(0);
    }

    @Override
    public String getCommandName() {
        return "exit";
    }
}
