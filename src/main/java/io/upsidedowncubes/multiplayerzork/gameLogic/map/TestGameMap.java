package io.upsidedowncubes.multiplayerzork.gameLogic.map;


import io.upsidedowncubes.multiplayerzork.gameLogic.item.DummyConsumable;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.DummyWeapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.DummyMonster;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestGameMap extends GameMap {
    private final int MAP_WIDTH = 3;
    private final int MAP_HEIGHT = 3;

    public TestGameMap(){
        mapName = "basic";

        map = new ArrayList<>();
        for (int row = 0; row < MAP_HEIGHT; row++){
            List<Room> temp = new ArrayList<>();
            for (int col = 0; col < MAP_WIDTH; col++){
                temp.add( new Room(row, col) );
            }
            map.add(temp);
        }


        map.get(0).get(0).setExit( false, false, false, true );

        map.get(0).get(1).setExit(  false, false, true, false );
        map.get(0).get(1).addItem( new DummyWeapon() );
        map.get(0).get(1).addMonster( new DummyMonster() );

        map.get(0).get(2).setExit(  false, true, false, true );
        map.get(0).get(2).addMonster( new DummyMonster() );

        map.get(1).get(0).setExit( true, false, false, true );
        map.get(1).get(0).addItem( new DummyConsumable() );

        map.get(1).get(1).setExit( false, false, true, false );
        map.get(1).get(1).addItem( new DummyConsumable() );

        map.get(1).get(2).setExit( true, true, false, true );

        map.get(2).get(0).setExit( true, false, true, false );
        map.get(2).get(0).addMonster( new DummyMonster() );

        map.get(2).get(1).setExit( false, true, true, false );

        map.get(2).get(2).setExit( true, true, false, false );

    }

    @Override
    public Location getStartingLoc() {
        return new Location(0,0);
    }
}
