package upsidedowncubes.multiplayerzork.gameLogic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import upsidedowncubes.multiplayerzork.gameLogic.command.Command;
import upsidedowncubes.multiplayerzork.gameLogic.command.CommandFactory;
import upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;

import java.util.List;
import java.util.Scanner;

@Component
public class Game implements CommandLineRunner {

    @Autowired
    private CommandParser commandParser;

    @Autowired
    private CommandFactory commandFactory;

    @Override
    public void run(String... args) throws Exception{
        String msg =
                "============================" +
                "Welcome to the world of Zork" +
                "Type 'help' to see the available commands" +
                "============================"
        ;
        System.out.println(msg);

        Scanner in = new Scanner(System.in);
        while (true){

            // get input ===========================
            System.out.print(">>> ");
            String s = in.nextLine();

            // parse the command ===========================
            List<String> cmd = commandParser.parse(s);

            // handle the command ===========================
            commandRunner(cmd);

            System.out.println();
        }

    }

    private void commandRunner(List<String> cmdAsList){
        if (cmdAsList == null){ // if invalid command
            System.out.println("I don't think I recognize that action...");
            return;
        }
        // get command
        Command cmd = CommandFactory.getCommand(cmdAsList.get(0));
        if (cmd == null){
            System.out.println("I don't think I recognize that action...");
            return;
        }

        cmd.execute(cmdAsList);

    }

}
