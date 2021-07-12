package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import java.util.List;

public interface Command {

    void execute(List<String> args);

    String getCommandName();

    boolean callableNow();

    int requiredArgs();

    String getDescription();
}
