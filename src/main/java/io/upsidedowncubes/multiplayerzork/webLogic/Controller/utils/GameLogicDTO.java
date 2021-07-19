package io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameLogicDTO {

    // 0 -> Command
    // 1 -> Message
    // 2 -> Game Output
    // 3 -> Error message
    private int type;

    // username of the sender
    private String sender;

    // what the sender sends
    private String content;

    // the session ID of the session that the user is in
    private int session;

    // FOR CURRENT USER
    private int attack;
    private int hp;
    private int maxHp;
    private int currentLoad;
    private int capacity;
    private String roomDescription;
}
