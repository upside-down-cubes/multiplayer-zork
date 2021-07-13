package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

public class UserSessionHandler {
    String username;
    String chatroom;

    UserSessionHandler(String[] userInfo) {
        this.username = userInfo[0];
        this.chatroom = userInfo[1];
    }
}
