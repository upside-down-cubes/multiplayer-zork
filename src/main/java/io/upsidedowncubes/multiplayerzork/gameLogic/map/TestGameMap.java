package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.Item;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.common.Goblin;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.common.Slime;
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
                temp.add( new Room(row, col, this) );
            }
            map.add(temp);
        }

        /*
        __________________
        [    ][______    ]
        [    ][______    ]
        [________________]
        */

        map.get(0).get(0)
                .setExit( false, false, false, true )
                .setDescription("Seems like an entrance to some kind of dungeon...");
        Room start = map.get(0).get(0);
        for (Item item : ItemFactory.getAllItem()){
            start.addItem( item );
        }

        map.get(0).get(1)
                .setExit(  false, false, true, false )
                .setDescription("Seems like an entrance to some kind of dungeon...")
                .addItem( ItemFactory.getItem("sword") )
                .addMonster( new Goblin() );

        map.get(0).get(2).setExit(  false, true, false, true )
                .addMonster( new Goblin() );

        map.get(1).get(0).setExit( true, false, false, true )
                .addItem( ItemFactory.getItem("potion") );

        map.get(1).get(1).setExit( false, false, true, false )
                .addItem( ItemFactory.getItem("potion") );

        map.get(1).get(2).setExit( true, true, false, true );

        map.get(2).get(0).setExit( true, false, true, false )
                .addMonster( new Slime() );

        map.get(2).get(1).setExit( false, true, true, false );

        map.get(2).get(2).setExit( true, true, false, false );

    }

    @Override
    public Location getStartingLoc() {
        return new Location(0,0);
    }

    @Override
    public int[] getRowColLimit() {
        return new int[]{ MAP_HEIGHT, MAP_WIDTH };
    }


}
