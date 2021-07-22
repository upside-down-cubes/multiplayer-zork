package io.upsidedowncubes.multiplayerzork.gameLogic.item.weapon;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.player.Player;
import org.springframework.stereotype.Component;

@Component
public class HeartySword implements Item, Weapon {

    @Override
    public String getName() {
        return "hearty_sword";
    }

    @Override
    public int getItemID() {
        return 6;
    }

    @Override
    public int getAttackStat(Player p) {
        return p.getHp() / 10;
    }

    @Override
    public void passiveEffect(Player p) {
        p.increaseCritRate(0.15);
    }

    @Override
    public void activeEffect(Player p) {

    }


}
