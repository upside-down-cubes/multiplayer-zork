package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CommandParser {

    private final String COMMAND_PREFIX = "/";

    public List<String> parse(String input) {
        // clean string
        input = input.trim();
        if (!input.startsWith(COMMAND_PREFIX)) {
            List<String> txt = new ArrayList<>();
            txt.add(input);
            return txt;
        }

        // loop through list of valid command (retrieved from CommandFactory)
        for (String cmd : CommandFactory.COMMAND_NAME_LIST) {

            // if the input is exactly the command
            if (input.equals(COMMAND_PREFIX + cmd)) {
                return Collections.singletonList(input);
            }

            // if the input starts with the command (followed by a space)
            else if (input.startsWith(COMMAND_PREFIX + cmd + " ")) {
                List<String> cmdAsList = new ArrayList<>();
                cmdAsList.add(COMMAND_PREFIX + cmd);
                String[] temp = input
                        .trim()
                        .substring(cmd.length() + 2)
                        .split(" ");
                cmdAsList.addAll(Arrays.asList(temp));
                return cmdAsList;
            }
        }

        return null;
    }

    private void text(String msg, String username) {
        MessageCenter.getUserMessageOut(username).printToAll("[ " + username + " ] says: " + msg);
    }


    // returns -1 if terminate
    // returns 0 if game
    // returns 1 if chat
    public int commandRunner(List<String> cmdAsList, String username) {

        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

//        if (cmdAsList == null){
//            System.out.println("LOG: cmdAsList is null");
//        }
//        else{
//            System.out.print("LOG: parsed command = [");
//            for (String s : cmdAsList){
//                System.out.print("\"" + s + "\" , ");
//            }
//            System.out.print("]");
//            System.out.println();
//        }

        if (cmdAsList == null) {
            messageOut.printToUser("Invalid command");
            return 0;
        } else if (!cmdAsList.get(0).startsWith(COMMAND_PREFIX)) {
            text(cmdAsList.get(0), username);
            return 1;
        }

        // get command
        Command cmd = CommandFactory.getCommand(cmdAsList.get(0).split("[/]")[1]);

        // if the command is not in the right state of use
        // (maybe player use Menu command while in game mode)
        if (!cmd.callableNow(username)) {
            messageOut.printToUser("Unable to use that right now!");
        }
        // check if the command has enough argument
        // [go].size        <=  (required 1)     --> fails
        // [go, north].size <=  (required 1)     --> doesnt fail
        else if (cmdAsList.size() <= cmd.requiredArgs() && cmd.requiredArgs() != -1) {
            messageOut.printToUser("That's not how you use the command!");
        } else {
            cmd.execute(cmdAsList, username);
            if (cmd instanceof Terminator && ((Terminator) cmd).willTerminate()) {
                return -1;
            }
        }

        return 0;

    }


}
