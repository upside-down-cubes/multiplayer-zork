package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class GameSessionHandler {
    private final Game game;
    private int count;
    private final Set<String> sessionMembers;

    GameSessionHandler() {
        this.game = new Game();
        this.count = 0;
        sessionMembers = new HashSet<>();
    }

    public void decrement(String username) {
        this.count--;
        sessionMembers.remove(username);
    }

    public void increment(String username) {
        this.count++;
        sessionMembers.add(username);
    }

    public Set<String> getAllMembers() {
        return sessionMembers;
    }
}