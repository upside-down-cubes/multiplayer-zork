package io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class GameLogicDTO {

    // 0 -> Command
    // 1 -> Message
    // 2 -> Game Output
    // 3 -> Error message
    private int type;

    // what the sender sends
    private String content;

    private Set<String> otherUsers;

    // FOR CURRENT USER
    private int attack;
    private int hp;
    private int maxHp;
    private int exp;
    private int maxExp;
    private int currentLoad;
    private int capacity;
    private String mapName;
    private String roomDescription;
}
