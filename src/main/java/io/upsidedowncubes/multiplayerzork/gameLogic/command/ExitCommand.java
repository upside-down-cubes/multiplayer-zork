package io.upsidedowncubes.multiplayerzork.gameLogic.command;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;

import java.util.List;

//@Component
//public class ExitCommand implements Command{
//
//    @Autowired
//    private ApplicationContext ac;
//
//    @Override
//    public void execute(List<String> args, String username) {
//        MessageOutput.printToAll("Thanks for playing!");
//        SpringApplication.exit(ac);
//        System.exit(0);
//    }
//
//    @Override
//    public String getCommandName() {
//        return "exit";
//    }
//
//    @Override
//    public boolean callableNow(String username) {
//        return ! OurWebSocketHandler.getGameByUser(username).gameInProcess();
//    }
//
//    @Override
//    public int requiredArgs() {
//        return 0;
//    }
//
//}
