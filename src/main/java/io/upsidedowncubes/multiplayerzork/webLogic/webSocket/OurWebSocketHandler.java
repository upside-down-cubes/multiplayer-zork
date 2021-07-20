package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.command.CommandParser;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Location;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.messageoutput.UserStateGenerator;
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
import java.util.Set;

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
        MessageCenter.addUser(thisUser.getUsername());
        MessageOutput messageOut = MessageCenter.getUserMessageOut(thisUser.getUsername());

        String[] msg = {
                "============================",
                "Welcome to the world of Zork. ",
                "You are in chat room " + thisUser.getChatroom() + ". ",
                "Press the [i] button near the chat box to see list of commands. ",
                "Press the [EXIT] button near the chat box to leave the chat session. ",
                "Press the [game controller] button to switch back and forth between \"command mode\" and \"chat mode\"",
                "============================"
        };

        messageOut.printToUser(msg); // print welcome to the user
        webSocketSessions.put(session, thisUser);
        messageOut.printToOthers(thisUser.getUsername() + " has joined the chatroom, " + thisUser.getChatroom()); // notify other user

        PlayerEntity player = PLAYER_REPOSITORY.findByUsername(thisUser.getUsername());
        player.setSessionID(thisUser.getChatroom());

        if (! CHATROOM_TO_GAME.containsKey(thisUser.getChatroom())) {
            CHATROOM_TO_GAME.put(thisUser.getChatroom(), new GameSessionHandler());
        }
        USERNAME_TO_CHATROOM.put(thisUser.getUsername(), thisUser.getChatroom());
        CHATROOM_TO_GAME.get(thisUser.getChatroom()).increment(thisUser.getUsername());
        if (CHATROOM_TO_GAME.get(thisUser.getChatroom()).getGame().gameInProcess()) {
            Location loc = CHATROOM_TO_GAME.get(thisUser.getChatroom()).getGame().getMap().getStartingLoc();
            player.setRow(loc.getRow());
            player.setCol(loc.getCol());
        }
        PLAYER_REPOSITORY.save(player);
    }

    private void toggleIsAlive(String username, Boolean isAlive) {
        PlayerEntity playerEntity = PLAYER_REPOSITORY.findByUsername(username);
        playerEntity.setIsAlive(isAlive);
        PLAYER_REPOSITORY.save(playerEntity);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        int newUser = 0;
        String[] splitMessage = message.getPayload().split(":");
        if ((!webSocketSessions.containsKey(session)) && splitMessage.length != 2) {
            return;
        }
        int gameStatus = 0;
        if (!webSocketSessions.containsKey(session)) {
            newUserJoined(session, splitMessage);
            toggleIsAlive(webSocketSessions.get(session).getUsername(), true);
            newUser = 1;
        } else if (!PLAYER_REPOSITORY.findByUsername(webSocketSessions.get(session).getUsername()).getIsAlive()) {
            session.close(new CloseStatus(1000, "Player is dead."));
        } else {
            CommandParser commandParser = (CommandParser) ContextAwareClass.getApplicationContext().getBean("commandParser");
            List<String> cmd = commandParser.parse(message.getPayload());
            gameStatus = commandParser.commandRunner(cmd, webSocketSessions.get(session).getUsername());
        }
        broadcastGameOutput(session, gameStatus, newUser);
        if (gameStatus == -1) {
            session.sendMessage( new TextMessage(
                    UserStateGenerator.getJson(
                            webSocketSessions.get(session).getUsername(),
                            "You have now exited the game session.\n" +
                            "Refresh the page to select new chatroom or press exit button to go back to home.", -1)
            ));
            toggleIsAlive(webSocketSessions.get(session).getUsername(), false);
        }
    }

    public static Game getGameByUser(String username) {
        return CHATROOM_TO_GAME.get(USERNAME_TO_CHATROOM.get(username)).getGame();
    }

    public static Set<String> getAllUsersInSameSession(String username) {
        return CHATROOM_TO_GAME.get(USERNAME_TO_CHATROOM.get(username)).getAllMembers();
    }

    public static boolean inSameSession(String user_username, String target_username) {
        return USERNAME_TO_CHATROOM.get(user_username).equals( USERNAME_TO_CHATROOM.get(target_username) );
    }

    private void broadcastGameOutput(WebSocketSession session, int gameStatus, int newUser) throws IOException {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(webSocketSessions.get(session).getUsername());

        List<String> DMMessage = messageOut.getAllOutput_DM();
        for (WebSocketSession webSocketSession : webSocketSessions.keySet()) {
            String username = webSocketSessions.get(webSocketSession).getUsername();

            if (DMMessage != null && username.equals(DMMessage.get(1))) {
                webSocketSession.sendMessage( new TextMessage(
                        UserStateGenerator.getJson(
                                username, DMMessage.get(2), gameStatus - newUser)
                ));
            } else if (session.equals(webSocketSession) && !messageOut.getAllOutput_user().isBlank()) {
                webSocketSession.sendMessage( new TextMessage(
                        UserStateGenerator.getJson(
                                username, messageOut.getAllOutput_user(), gameStatus - newUser)
                ));
            } else if (webSocketSessions.get(session).getChatroom().equals(webSocketSessions.get(webSocketSession).getChatroom())
                        && !messageOut.getAllOutput().isBlank()) {
                webSocketSession.sendMessage( new TextMessage(
                        UserStateGenerator.getJson(
                                username, messageOut.getAllOutput(), gameStatus - newUser)
                ));
            }
        }
        messageOut.clear();
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UserSessionHandler thisUser = webSocketSessions.get(session);
        CHATROOM_TO_GAME.get(thisUser.getChatroom()).decrement(thisUser.getUsername());
        if (CHATROOM_TO_GAME.get(thisUser.getChatroom()).getCount() == 0) {
            CHATROOM_TO_GAME.remove(thisUser.getChatroom());
        }
        USERNAME_TO_CHATROOM.remove(thisUser.getUsername());
        PlayerEntity player = PLAYER_REPOSITORY.findByUsername(thisUser.getUsername());
        player.setRow(-1);
        player.setCol(-1);
        player.setSessionID(null);
        PLAYER_REPOSITORY.save(player);
        toggleIsAlive(webSocketSessions.get(session).getUsername(), null);
        MessageCenter.removeUser(thisUser.getUsername() );
        webSocketSessions.remove(session);


    }
}
