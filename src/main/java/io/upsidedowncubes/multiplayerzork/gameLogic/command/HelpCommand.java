package io.upsidedowncubes.multiplayerzork.gameLogic.command;


import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.List;

// PROBABLY BECOME USELESS NOW THAT WE HAVE FRONTEND
// BUT WE WILL KEEP IT FOR NOW UNTIL EVERYTHING WORKS

public class HelpCommand implements Command{

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public void execute(List<String> args, String username) {
        MessageOutput.printToUser("HELP======================");

        for (String name : CommandFactory.COMMAND_NAME_LIST){
            Command cmd = CommandFactory.getCommand(name);
            if ( cmd.callableNow(username) ){
                MessageOutput.printToUser(name);
            }
        }
        MessageOutput.printToUser("HELP======================");
    }

    @Override
    public boolean callableNow(String username) {
        // will always be able to call
        return true;
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
