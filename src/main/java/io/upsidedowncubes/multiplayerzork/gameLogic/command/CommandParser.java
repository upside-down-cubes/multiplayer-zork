package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CommandParser {

    private final String COMMAND_PREFIX = "/";

    public List<String> parse(String input){
        // clean string
        input = input.trim();
        if (! input.startsWith(COMMAND_PREFIX)){
            List<String> txt = new ArrayList<>();
            txt.add(input);
            return txt;
        }

        // loop through list of valid command (retrieved from CommandFactory)
        for ( String cmd : CommandFactory.COMMAND_NAME_LIST){

            // if the input is exactly the command
            if (input.equals( COMMAND_PREFIX + cmd )){
                return Collections.singletonList(input);
            }

            // if the input starts with the command (followed by a space)
            else if ( input.startsWith(COMMAND_PREFIX + cmd + " ") ){
                List<String> cmdAsList = new ArrayList<>();
                cmdAsList.add(COMMAND_PREFIX + cmd);
                String[] temp = input
                        .trim()
                        .substring(cmd.length()+2)
                        .split(" ");
                cmdAsList.addAll( Arrays.asList( temp ) );
                return cmdAsList;
            }
        }

        return null;
    }

    private void text(String msg, String username){
        MessageOutput.printToAll("[ " + username + " ] says: " + msg);
    }


    // returns true if user wants to quit
    public boolean commandRunner(List<String> cmdAsList, String username){

        if (cmdAsList == null){
            MessageOutput.printToUser("Invalid command");
            return false;
        }
        else if ( ! cmdAsList.get(0).startsWith( COMMAND_PREFIX )){
            text(cmdAsList.get(0), username);
            return false;
        }

        // get command
        Command cmd = CommandFactory.getCommand(cmdAsList.get(0).split("[/]")[1]);


        boolean quit = false;

        // if the command is not in the right state of use
        // (maybe player use Menu command while in game mode)
        if ( !cmd.callableNow(username) ){
            MessageOutput.printToUser("Unable to use that right now!");
        }
        // check if the command has enough argument
        // [go].size        <=  (required 1)     --> fails
        // [go, north].size <=  (required 1)     --> doesnt fail
        else if ( cmdAsList.size() <= cmd.requiredArgs() && cmd.requiredArgs() != -1){
            MessageOutput.printToUser("That's not how you use the command!");
        }
        else{
            cmd.execute(cmdAsList, username);
            if (cmd instanceof Terminator){
                quit = ((Terminator) cmd).willTerminate();
            }
        }

        return quit;

    }


}
