package upsidedowncubes.multiplayerzork.gameLogic.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class CommandFactory {

    // inject all command classes into this list
    @Autowired
    private List<Command> COMMAND_LIST;

    // command name list + command map
    protected static List<String> COMMAND_NAME_LIST;
    protected static Map<String, Command> COMMAND_MAP;

    @PostConstruct
    private void init(){
        // initialize List with all valid command names
        COMMAND_NAME_LIST = new ArrayList<>();
        // initialize Mapping of Command instances with its name --> Key:"cmd_name" , Value: new Command();
        COMMAND_MAP = new HashMap<>();

        for (Command cmd : COMMAND_LIST){
            COMMAND_MAP.put( cmd.getCommandName() , cmd );
            COMMAND_NAME_LIST.add(cmd.getCommandName());
            System.out.println(cmd.getCommandName());
        }
    }

    // get the command that corresponds to the given String
    public static Command getCommand(String str){
        return COMMAND_MAP.get(str);
    }

}
