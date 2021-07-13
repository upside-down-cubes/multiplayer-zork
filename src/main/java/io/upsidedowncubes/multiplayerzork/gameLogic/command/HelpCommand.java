package io.upsidedowncubes.multiplayerzork.gameLogic.command;


import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.stereotype.Component;

import java.util.List;

// PROBABLY BECOME USELESS NOW THAT WE HAVE FRONTEND
// BUT WE WILL KEEP IT FOR NOW UNTIL EVERYTHING WORKS

@Component
public class HelpCommand implements Command{

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "This command prints out the 'currently' available commands and their descriptions";
    }

    @Override
    public void execute(List<String> args) {
        MessageOutput.print("HELP======================");

        for (String name : CommandFactory.COMMAND_NAME_LIST){
            Command cmd = CommandFactory.getCommand(name);
            if ( cmd.callableNow() ){
                MessageOutput.print(name + " :  " + cmd.getDescription());
            }
        }
        MessageOutput.print("==========================");
    }

    @Override
    public boolean callableNow() {
        // will always be able to call
        return true;
    }

    @Override
    public int requiredArgs() {
        return 0;
    }
}
