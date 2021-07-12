package io.upsidedowncubes.multiplayerzork.gameLogic;


import io.upsidedowncubes.multiplayerzork.gameLogic.command.Command;
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
        MessageOutput.init();
        MessageOutput.print(msg);
        System.out.println( MessageOutput.getAllOutput() );

        Scanner in = new Scanner(System.in);
        while (true){

            // get input ===========================
            System.out.print(">>> ");
            String s = in.nextLine();

            // parse the command ===========================
            List<String> cmd = commandParser.parse(s);

            // handle the command ===========================
            MessageOutput.init();
            commandRunner(cmd);

            MessageOutput.print("");
            System.out.println( MessageOutput.getAllOutput() );
        }

    }

    private void commandRunner(List<String> cmdAsList){
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


    public void setPlayer(Player player){ this.player = player; }
    public Player getPlayer(){ return player; }
    public Inventory getInventory(){ return player.getBag(); }

}
