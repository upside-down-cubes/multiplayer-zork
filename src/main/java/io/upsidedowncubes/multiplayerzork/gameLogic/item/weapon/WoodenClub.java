package io.upsidedowncubes.multiplayerzork.gameLogic.item.weapon;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import org.springframework.stereotype.Component;

@Component
public class WoodenClub implements Item, Weapon {

    // drop from some monsters that are likely to wield a weapon (goblins and such)

    @Override
    public String getName() {
        return "wooden_club";
    }

    @Override
    public int getItemID() {
        return 3;
    }

    @Override
    public int getAttackStat(Player p) {
        return 2;
    }

    @Override
    public void passiveEffect(Player p) {
        p.increaseCritMultiplier(0.2);
    }

    @Override
    public void activeEffect(Player p) {

    }
}
