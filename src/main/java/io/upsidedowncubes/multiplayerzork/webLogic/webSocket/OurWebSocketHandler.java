package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OurWebSocketHandler extends TextWebSocketHandler {
    // use this to map username with corresponding session
    private final Map<WebSocketSession, UserSessionHandler> webSocketSessions = new HashMap<>();
    private static final Map<String, Game> CHATROOM_TO_GAME = new HashMap<>();
    private static final Map<String, String> USERNAME_TO_CHATROOM = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO: uncomment and fix when Pat pushes his commands
//        String[] msg = {
//                "============================",
//                "Welcome to the world of Zork",
//                "Type 'help' to see the available commands",
//                "============================"
//        };
//        MessageOutput.init();
//        MessageOutput.print(msg);
//        session.sendMessage(MessageOutput.getAllOutput());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String[] splitMessage = message.getPayload().split(":");
        if ((!webSocketSessions.containsKey(session)) && splitMessage.length != 2) {
            return;
        }
        if (!webSocketSessions.containsKey(session)) {
            UserSessionHandler thisUser = new UserSessionHandler(splitMessage[0], splitMessage[1]);

            webSocketSessions.put(session, thisUser);
            message = new TextMessage(thisUser.username + " has joined the chatroom, " + thisUser.chatroom);
            if (! CHATROOM_TO_GAME.containsKey(thisUser.chatroom)) {
                CHATROOM_TO_GAME.put(thisUser.chatroom, new Game());
                USERNAME_TO_CHATROOM.put(thisUser.username, thisUser.chatroom);
            }
        } else {
            CommandParser commandParser = (CommandParser) ContextAwareClass.getApplicationContext().getBean("commandParser");
            List<String> cmd = commandParser.parse(message.getPayload());
            MessageOutput.init();
            commandParser.commandRunner(cmd);
            message = new TextMessage(MessageOutput.getAllOutput());
        }
        broadcastGameOutput(session, message);
    }

    public static Game getGameByUser(String username) {
        return CHATROOM_TO_GAME.get(USERNAME_TO_CHATROOM.get(username));
    }

    private void broadcastGameOutput(WebSocketSession session, TextMessage message) throws IOException {
        // this is what should work
        // commandParser.commandRunner(cmd, username);
        for (WebSocketSession webSocketSession : webSocketSessions.keySet()){
            // TODO: uncomment and fix when Pat pushes his commands
//            if (session.equals(webSocketSession)) {
//                webSocketSession.sendMessage(MessageOutput.getAllOutput_user());
//
            // this is for personalize stuff
            //else
            if (webSocketSessions.get(session).chatroom.equals(webSocketSessions.get(webSocketSession).chatroom)) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UserSessionHandler thisUser = webSocketSessions.get(session);
        CHATROOM_TO_GAME.remove(thisUser.chatroom);
        USERNAME_TO_CHATROOM.remove(thisUser.username);
        webSocketSessions.remove(session);

    }
}
