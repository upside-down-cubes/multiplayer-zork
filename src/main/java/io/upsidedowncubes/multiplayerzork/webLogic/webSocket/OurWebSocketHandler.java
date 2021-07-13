package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OurWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();

    // use this to map username with corresponding session
    private Map<WebSocketSession, String> usernameMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        // TODO: if we get the username here then life's easy, if not then fuck it

        webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // TODO: get username from session (HINT: make a HashMap<session, username>)
        String currentUsername = usernameMap.get( session );

        CommandParser commandParser = (CommandParser) ContextAwareClass.getApplicationContext().getBean("commandParser");
        List<String> cmd = commandParser.parse(message.getPayload());
        MessageOutput.init();
        commandParser.commandRunner(cmd);

        // this is what should work
        // commandParser.commandRunner(cmd, username);

        for(WebSocketSession webSocketSession : webSocketSessions){

            // this is for personalize stuff
            if (webSocketSession.equals(session)){
                // print the message for the sender
                // webSocketSession.sendMessage( new TextMessage( MessageOutput.getAllOutput() ) );
            }

            webSocketSession.sendMessage(new TextMessage(">>> " + message.getPayload()));
            webSocketSession.sendMessage(new TextMessage( MessageOutput.getAllOutput() ));
            // in case
            // webSocketSession.sendMessage(new TextMessage( MessageOutput.getJsonOutput() ));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketSessions.remove(session);
    }
}
