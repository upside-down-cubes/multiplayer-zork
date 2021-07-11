package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import org.springframework.stereotype.Component;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.List;

@Component
public class DummyCommand implements Command{

    @Override
    public void execute(List<String> args) {
        MessageOutput.print("LOG: This is test");
    }

    @Override
    public String getCommandName() {
        return "dummy";
    }

}
