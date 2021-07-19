package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import org.springframework.stereotype.Component;

@Component
public class Sword implements Item, Weapon{
    @Override
    public String getName() {
        return "sword";
    }

    @Override
    public int getItemID() {
        return 2;
    }

    @Override
    public int getAttackStat() {
        return 3;
    }
}
