package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

import io.upsidedowncubes.multiplayerzork.gameLogic.command.Command;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class OurWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(WebSocketSession webSocketSession : webSocketSessions){
            webSocketSession.sendMessage(new TextMessage(">>> " + message.getPayload()));
            CommandParser commandParser = (CommandParser) ContextAwareClass.getApplicationContext().getBean("commandParser");
            List<String> cmd = commandParser.parse(message.getPayload());
            MessageOutput.init();
            commandRunner(cmd);
            MessageOutput.print("");
            webSocketSession.sendMessage(new TextMessage( MessageOutput.getAllOutput() ));
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

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketSessions.remove(session);
    }
}
