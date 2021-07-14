package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

import lombok.Getter;

@Getter
public class UserSessionHandler {
    private final String username;
    private final String chatroom;

    UserSessionHandler(String username, String chatroom) {
        this.username = username;
        this.chatroom = chatroom;
    }
}
