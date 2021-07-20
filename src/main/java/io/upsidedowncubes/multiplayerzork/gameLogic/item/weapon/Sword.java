package io.upsidedowncubes.multiplayerzork.gameLogic.item.weapon;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Sword implements Item, Weapon {
    @Override
    public String getName() {
        return "sword";
    }

    @Override
    public int getItemID() {
        return 2;
    }

    @Override
    public int getAttackStat(Player p) {
        return 3;
    }

    @Override
    public void passiveEffect(Player p) {
        p.increaseCritRate(0.2);
    }

    @Override
    public void activeEffect(Player p) {

    }
}
