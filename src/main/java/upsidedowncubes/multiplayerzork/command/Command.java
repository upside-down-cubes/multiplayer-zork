package upsidedowncubes.multiplayerzork.command;

import java.util.List;

public interface Command {

    void execute(List<String> args);

    String getCommandName();

}
