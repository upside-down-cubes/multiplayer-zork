package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import java.util.List;

public interface Command {

    void execute(List<String> args, String username);

    String getCommandName();

    boolean callableNow(String username);

    int requiredArgs();

    // String getDescription();
}
