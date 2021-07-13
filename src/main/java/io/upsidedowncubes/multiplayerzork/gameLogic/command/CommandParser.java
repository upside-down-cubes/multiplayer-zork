package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CommandParser {


    public List<String> parse(String input){
        // clean string
        input = input.trim();

        // loop through list of valid command (retrieved from CommandFactory)
        for ( String cmd : CommandFactory.COMMAND_NAME_LIST){

            // if the input is exactly the command
            if (cmd.equals(input)){
                return Collections.singletonList(input);
            }

            // if the input starts with the command (followed by a space)
            else if ( input.startsWith(cmd + " ") ){
                List<String> cmdAsList = new ArrayList<>();
                cmdAsList.add(cmd);
                String[] temp = input
                        .trim()
                        .substring(cmd.length()+1)
                        .split(" ");
                cmdAsList.addAll( Arrays.asList( temp ) );
                return cmdAsList;
            }
        }
        return null;
    }


    // TODO: make a version that receive the player's username and refactor *ALL* function to adapt to that

 // public void commandRunner(List<String> cmdAsList, String username){
    public void commandRunner(List<String> cmdAsList){
        if (cmdAsList == null){ // if invalid command
            MessageOutput.print("I don't think I recognize that action...");
            return;
        }
        // get command
        Command cmd = CommandFactory.getCommand(cmdAsList.get(0));

        // if the command is not in the right state of use
        // (maybe player use Menu command while in game mode)
        if ( !cmd.callableNow() ){
            MessageOutput.print("Unable to use that right now!");
        }
        // check if the command has enough argument
        // [go].size        <=  (required 1)     --> fails
        // [go, north].size <=  (required 1)     --> doesnt fail
        else if ( cmdAsList.size() <= cmd.requiredArgs() ){
            MessageOutput.print("That's not how you use the command!");
        }
        else{
            cmd.execute(cmdAsList);
            // checkObjective();
        }

    }

}
