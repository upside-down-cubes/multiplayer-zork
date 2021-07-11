package upsidedowncubes.multiplayerzork.gameLogic.command;

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

}
