package io.upsidedowncubes.multiplayerzork.gameLogic;


import io.upsidedowncubes.multiplayerzork.gameLogic.command.Command;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import java.util.List;
import java.util.Scanner;

@Component
public class Game implements CommandLineRunner {

    @Autowired
    private CommandParser commandParser;

    @Autowired
    private CommandFactory commandFactory;

    @Override
    public void run(String... args) throws Exception {
        String[] msg = {
                "============================",
                "Welcome to the world of Zork",
                "Type 'help' to see the available commands",
                "============================"
        };
        MessageOutput.print(msg);

        Scanner in = new Scanner(System.in);
        while (true){

            // get input ===========================
            System.out.print(">>> ");
            String s = in.nextLine();

            // parse the command ===========================
            List<String> cmd = commandParser.parse(s);

            // handle the command ===========================
            commandRunner(cmd);

            MessageOutput.print("");
        }

    }

    private void commandRunner(List<String> cmdAsList){
        if (cmdAsList == null){ // if invalid command
            MessageOutput.print("I don't think I recognize that action...");
            return;
        }
        // get command
        Command cmd = CommandFactory.getCommand(cmdAsList.get(0));
        if (cmd == null){
            MessageOutput.print("I don't think I recognize that action...");
            return;
        }

        cmd.execute(cmdAsList);

    }

}
