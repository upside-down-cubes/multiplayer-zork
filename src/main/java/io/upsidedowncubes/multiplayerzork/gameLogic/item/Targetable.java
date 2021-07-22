package io.upsidedowncubes.multiplayerzork.gameLogic.item;

public interface Targetable {

    // this is actually a use command, but on other people
    // so basically consumable but not for self
    void useOn(String user_username, String target_username);

}
