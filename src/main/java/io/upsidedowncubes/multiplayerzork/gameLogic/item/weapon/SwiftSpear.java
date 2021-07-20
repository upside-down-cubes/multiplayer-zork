package io.upsidedowncubes.multiplayerzork.gameLogic.item.weapon;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import org.springframework.stereotype.Component;

@Component
public class SwiftSpear implements Item, Weapon {

    @Override
    public String getName() {
        return "swift_spear";
    }

    @Override
    public int getItemID() {
        return 7;
    }

    @Override
    public int getAttackStat(Player p) {
        return (p.getMaxHP() - 30) / 10;
    }

    @Override
    public void passiveEffect(Player p) {
        p.increaseCritRate(0.2);
    }

    @Override
    public void activeEffect(Player p) {

    }
}
