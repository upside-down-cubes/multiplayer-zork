package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OurWebSocketHandler extends TextWebSocketHandler {
    private final static PlayerRepository PLAYER_REPOSITORY = (PlayerRepository) ContextAwareClass
            .getApplicationContext().getBean("playerRepository");
    // use this to map username with corresponding session
    private final Map<WebSocketSession, UserSessionHandler> webSocketSessions = new HashMap<>();

    private static final Map<String, GameSessionHandler> CHATROOM_TO_GAME = new HashMap<>();
    private static final Map<String, String> USERNAME_TO_CHATROOM = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    private void newUserJoined(WebSocketSession session, String[] splitMessage) {
        UserSessionHandler thisUser = new UserSessionHandler(splitMessage[0], splitMessage[1]);

        MessageOutput.clear();
        String[] msg = {
                "============================",
                "Welcome to the world of Zork",
                "You are in chat room " + thisUser.getChatroom(),
                "Type 'help' to see the available commands",
                "============================"
        };

        MessageOutput.printToUser(msg); // print welcome to the user
        webSocketSessions.put(session, thisUser);
        MessageOutput.printToOthers(thisUser.getUsername() + " has joined the chatroom, " + thisUser.getChatroom()); // notify other user

        PlayerEntity player = PLAYER_REPOSITORY.findByUsername(thisUser.getUsername());
        player.setSessionID(thisUser.getChatroom());
        PLAYER_REPOSITORY.save(player);

        if (! CHATROOM_TO_GAME.containsKey(thisUser.getChatroom())) {
            CHATROOM_TO_GAME.put(thisUser.getChatroom(), new GameSessionHandler());
        }
        USERNAME_TO_CHATROOM.put(thisUser.getUsername(), thisUser.getChatroom());
        CHATROOM_TO_GAME.get(thisUser.getChatroom()).increment();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String[] splitMessage = message.getPayload().split(":");
        if ((!webSocketSessions.containsKey(session)) && splitMessage.length != 2) {
            return;
        }
        boolean quit = false;
        if (!webSocketSessions.containsKey(session)) {
            newUserJoined(session, splitMessage);
        }
        else {
            CommandParser commandParser = (CommandParser) ContextAwareClass.getApplicationContext().getBean("commandParser");
            List<String> cmd = commandParser.parse(message.getPayload());
            MessageOutput.clear();
            quit = commandParser.commandRunner(cmd, webSocketSessions.get(session).getUsername());
        }
        broadcastGameOutput(session);
        if (quit) {
            session.close(new CloseStatus(1000, "User quit the game."));
        }
    }

    public static Game getGameByUser(String username) {
        return CHATROOM_TO_GAME.get(USERNAME_TO_CHATROOM.get(username)).getGame();
    }


    private void broadcastGameOutput(WebSocketSession session) throws IOException {
        for (WebSocketSession webSocketSession : webSocketSessions.keySet()){
            if (session.equals(webSocketSession))
                webSocketSession.sendMessage( new TextMessage( MessageOutput.getJsonOutput_user() ) );
            else if (webSocketSessions.get(session).getChatroom().equals(webSocketSessions.get(webSocketSession).getChatroom())) {
                webSocketSession.sendMessage( new TextMessage( MessageOutput.getJsonOutput() ) );
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UserSessionHandler thisUser = webSocketSessions.get(session);
        CHATROOM_TO_GAME.get(thisUser.getChatroom()).decrement();
        if (CHATROOM_TO_GAME.get(thisUser.getChatroom()).getCount() == 0) {
            CHATROOM_TO_GAME.remove(thisUser.getChatroom());
        }
        USERNAME_TO_CHATROOM.remove(thisUser.getUsername());
        PlayerEntity player = PLAYER_REPOSITORY.findByUsername(thisUser.getUsername());
        player.setRow(-1);
        player.setCol(-1);
        player.setSessionID(null);
        PLAYER_REPOSITORY.save(player);
        webSocketSessions.remove(session);


    }
}
