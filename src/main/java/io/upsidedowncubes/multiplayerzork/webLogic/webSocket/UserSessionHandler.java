package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

// TODO: refactor -> getter + setter / make private
public class UserSessionHandler {
    String username;
    String chatroom;

    UserSessionHandler(String username, String chatroom) {
        this.username = username;
        this.chatroom = chatroom;
    }
}
