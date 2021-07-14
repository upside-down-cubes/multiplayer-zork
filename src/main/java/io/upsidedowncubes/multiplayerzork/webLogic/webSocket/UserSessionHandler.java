package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

public class UserSessionHandler {
    String username;
    String chatroom;

    UserSessionHandler(String username, String chatroom) {
        this.username = username;
        this.chatroom = chatroom;
    }
}
