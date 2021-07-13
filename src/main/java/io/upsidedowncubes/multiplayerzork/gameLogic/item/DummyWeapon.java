package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import org.springframework.stereotype.Component;

@Component
public class DummyWeapon implements Item, Weapon{
    @Override
    public String getName() {
        return "dummy_wp";
    }

    @Override
    public int getItemID() {
        return -2;
    }

    @Override
    public int getAttackStat() {
        return 1;
    }
}
