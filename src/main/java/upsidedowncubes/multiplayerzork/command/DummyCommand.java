package upsidedowncubes.multiplayerzork.command;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DummyCommand implements Command{

    @Override
    public void execute(List<String> args) {
        System.out.println("LOG: This is test");
    }

    @Override
    public String getCommandName() {
        return "dummy";
    }

}
