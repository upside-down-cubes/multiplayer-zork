package io.upsidedowncubes.multiplayerzork.webLogic.webSocket;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import lombok.Getter;

// TODO: refactor -> getter + setter / make private
@Getter
public class GameSessionHandler {
    private Game game;
    private int count;

    GameSessionHandler() {
        this.game = new Game();
        this.count = 0;
    }

    public void decrement() {
        this.count--;
    }

    public void increment() {
        this.count++;
    }
}