package io.upsidedowncubes.multiplayerzork.gameLogic;

import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMap;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
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

    GameMap map;
    Player player;

    private boolean isPlaying = false;



    @Override
    public void run(String... args) throws Exception {
        String[] msg = {
                "============================",
                "Welcome to the world of Zork",
                "Type 'help' to see the available commands",
                "============================"
        };
        MessageOutput.clear();
        MessageOutput.printToAll(msg);
        System.out.println( MessageOutput.getAllOutput() );

        Scanner in = new Scanner(System.in);
        while (true) {

            // get input ===========================
            System.out.print(">>> ");
            String s = in.nextLine();

            // parse the command ===========================
            List<String> cmd = commandParser.parse(s);

            // handle the command ===========================
            MessageOutput.clear();
            commandParser.commandRunner(cmd, "admin");

            MessageOutput.printToAll("");
            System.out.println(MessageOutput.getAllOutput());
        }

    }

    public void setGameState(boolean state){
        isPlaying = state;
    }
    public boolean gameInProcess(){
        return isPlaying;
    }


    public void setMap(GameMap map){
        this.map = map;
    }
    public GameMap getMap(){
        return map;
    }

}
