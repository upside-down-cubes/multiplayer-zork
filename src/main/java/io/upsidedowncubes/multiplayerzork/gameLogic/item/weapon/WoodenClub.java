package io.upsidedowncubes.multiplayerzork.gameLogic.item.weapon;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
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
        return 2;
    }

    @Override
    public int getAttackStat() {
        return 2;
    }
}
