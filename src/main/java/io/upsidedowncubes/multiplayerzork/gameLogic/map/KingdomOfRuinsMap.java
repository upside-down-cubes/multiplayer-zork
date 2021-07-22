package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import io.upsidedowncubes.multiplayerzork.gameLogic.item.ItemFactory;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.common.Goblin;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.common.Slime;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.common.Wolves;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.desert.Coyote;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.desert.Mummy;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.desert.SandTortoise;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.grassland.Bees;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.grassland.Deer;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.grassland.Mandrake;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.ruins.Bandits;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.swamp.Cobra;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.swamp.MudMonster;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.swamp.PoisonFrog;
import io.upsidedowncubes.multiplayerzork.gameLogic.monster.util.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static javax.swing.UIManager.put;

public class KingdomOfRuinsMap extends GameMap{

    private final int MAP_WIDTH = 10;
    private final int MAP_HEIGHT = 10;


    private final String GRASSLAND = "You are in a grassy field";
    private final String SWAMPLAND = "It looks and smells like a swamp";
    private final String DESERT= "All there is sand, sand and more sand, must be a desert";
    private final String FOREST = "You are in a forest";
    private final String SNOW = "Snow covers everything and it is freezing here";
    private final String RUINS = "It seems like the ruins of the big kingdom";

    private HashMap<Integer, String> itemMap = new HashMap<>();{{
        put(1, "potion");
        put(2, "sword");
        put(3, "wooden_club");
        put(4, "slime_jelly");
        put(5, "giant_berry");
        put(6, "hearty_sword");
        put(7, "swift_spear");
        put(8, "staff");
        put(9, "scroll_of_mastery");
        put(10, "teleporter");
    }
    };


    public KingdomOfRuinsMap(){
        mapName = "Kingdom_of_Ruins";

        map = new ArrayList<>();
        for (int row = 0; row < MAP_HEIGHT; row++){
            List<Room> temp = new ArrayList<>();
            for (int col = 0; col < MAP_WIDTH; col++){
                temp.add( new Room(row, col, this) );
            }
            map.add(temp);
        }
        List<Monster> swamp = spawnMonster("swamp", 5); // no boss yet
        List<Monster> grass = spawnMonster("grassland", 5); // no boss yet
        List<Monster> ruins = spawnMonster("ruins", 4); // no boss yet
        List<Monster> desert = spawnMonster("desert", 6); // no boss yet
        // List<Monster> snow = spawnMonster("snow", 4); // no boss yet


        // column 0
        getRoom(0,0)
                .setExit( false , false, true, true)
                .addMonster( swamp.get(0) )
                .setDescription(SWAMPLAND);

        getRoom(1,0)
                .setExit(true , false, false , true)
                .addItem(ItemFactory.getItem(itemMap.get(5)))
                .setDescription(SWAMPLAND);

        getRoom(2,0)
                .setExit(true, false, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .addItem(ItemFactory.getItem(itemMap.get(9)))
                .setDescription(SWAMPLAND);

        getRoom(3,0)
                .setExit(false , false, true, true)
                .setDescription(SWAMPLAND);

        getRoom(4,0)
                .setExit(true , false, true, true)
                .addMonster( swamp.get(1) )
                .setDescription(SWAMPLAND);

        getRoom(5,0)
                .setExit(true, false, true, true)
                .addMonster(grass.get(0))
                .setDescription(GRASSLAND);

        getRoom(6,0)
                .setExit(true , false, false, false)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .setDescription(GRASSLAND);

        getRoom(7,0)
                .setExit(false , false, true, true)
                .addItem(ItemFactory.getItem(itemMap.get(4)))
                .setDescription(GRASSLAND);

        getRoom(8,0)
                .setExit(true , false, false, true)
                .setDescription(GRASSLAND);

        getRoom(9,0)
                .setExit(true , false, true, false)
                .addMonster( grass.get(1) )
                .setDescription(GRASSLAND);

        // column 1
        getRoom(0,1)
                .setExit(false, true, true, false)
                .setDescription(SWAMPLAND);

        getRoom(1,1)
                .setExit(false, false, false, true)
                .addItem(ItemFactory.getItem(itemMap.get(6)))
                .addItem(ItemFactory.getItem(itemMap.get(8)))
                .addItem(ItemFactory.getItem(itemMap.get(9)))
                .setDescription(SWAMPLAND);

        getRoom(2,1)
                .setExit(true, true, false, false)
                .addMonster( swamp.get(2) ) // should be boss
                .setDescription(SWAMPLAND);

        getRoom(3,1)
                .setExit(false, true, false, true)
                .setDescription(SWAMPLAND);

        getRoom(4,1)
                .setExit(true, true, true, false)
                .setDescription(SWAMPLAND);

        getRoom(5,1)
                .setExit(false, true, true, true)
                .addItem(ItemFactory.getItem(itemMap.get(2)))
                .setDescription(GRASSLAND);

        getRoom(6,1)
                .setExit(true, true, false, true)
                .setDescription(GRASSLAND);

        getRoom(7,1)
                .setExit(true, true, false, true)
                .setDescription(GRASSLAND);

        getRoom(8,1)
                .setExit(true, false, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(5)))
                .setDescription(GRASSLAND);

        getRoom(9,1)
                .setExit(false , true, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .setDescription(GRASSLAND);

        // column 2
        getRoom(0,2)
                .setExit(false, true, false, true)
                .addMonster( swamp.get(3) )
                .setDescription(SWAMPLAND);

        getRoom(1,2)
                .setExit(true, false, false, true)
                .setDescription(SWAMPLAND);

        getRoom(2,2)
                .setExit(true, false, false, true)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .setDescription(SWAMPLAND);

        getRoom(3,2)
                .setExit(true, false, false, true)
                .addMonster( swamp.get(4) )
                .setDescription(SWAMPLAND);

        getRoom(4,2)
                .setExit(true, true, false, false)
                .setDescription(SWAMPLAND);

        getRoom(5,2)
                .setExit(true, true, true, false)
                .setDescription(GRASSLAND);

        getRoom(6,2)
                .setExit(false, false, false, true)
                .addMonster(grass.get(2))
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .addItem(ItemFactory.getItem(itemMap.get(2)))
                .setDescription(GRASSLAND);

        getRoom(7,2)
                .setExit(true, false, false, true)
                .addItem(ItemFactory.getItem(itemMap.get(8)))
                .setDescription(GRASSLAND);

        getRoom(8,2)
                .setExit(true, true, false, true)
                .setDescription(GRASSLAND);

        getRoom(9,2)
                .setExit(true, true, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .setDescription("This is the starting place"+GRASSLAND);

        // column 3
        getRoom(0,3)
                .setExit(false, false, true, true)
                .setDescription(SNOW);

        getRoom(1,3)
                .setExit(true, false ,false ,true)
                .addItem(ItemFactory.getItem(itemMap.get(10)))
                .setDescription(SNOW);

        getRoom(2,3)
                .setExit(true, false ,false ,true)
                .setDescription(SNOW);

        getRoom(3,3)
                .setExit(true, false ,false ,true)
                .addMonster() // snow.get(0) boss
                .setDescription(SNOW);

        getRoom(4,3)
                .setExit(true, false ,false ,false)
                .addMonster() // snow.get(1)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .addItem(ItemFactory.getItem(itemMap.get(7)))
                .addItem(ItemFactory.getItem(itemMap.get(8)))
                .setDescription(SNOW);

        getRoom(5,3)
                .setExit(false, true, true, true)
                .setDescription(FOREST);

        getRoom(6,3)
                .setExit(true, false ,false ,true)
                .addItem(ItemFactory.getItem(itemMap.get(7)))
                .setDescription(FOREST);

        getRoom(7,3)
                .setExit(true, false ,false ,true)
                .addMonster(grass.get(3))
                .setDescription(FOREST);

        getRoom(8,3)
                .setExit(true, false ,false ,false)
                .addItem(ItemFactory.getItem(itemMap.get(8)))
                .addItem(ItemFactory.getItem(itemMap.get(9)))
                .setDescription(FOREST);

        getRoom(9,3)
                .setExit(false, true, true, false)
                .setDescription(FOREST);

        // column 4
        getRoom(0,4)
                .setExit(false, true, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .setDescription(SNOW);

        getRoom(1,4)
                .setExit(true, false, false, true)
                .addMonster() // snow.get(2)
                .setDescription(SNOW);

        getRoom(2,4)
                .setExit(true, false, true, false)
                .setDescription(SNOW);

        getRoom(3,4)
                .setExit(false, false, true, true)
                .setDescription(FOREST);

        getRoom(4,4)
                .setExit(true, false, false,true)
                .addMonster( grass.get(0) )
                .setDescription(FOREST);

        getRoom(5,4)
                .setExit(true, true, true, true)
                .addItem(ItemFactory.getItem(itemMap.get(4)))
                .setDescription(FOREST);

        getRoom(6,4)
                .setExit(true, false, false, true)
                .setDescription(FOREST);

        getRoom(7,4)
                .setExit(true, false, false, true)
                .setDescription(FOREST);

        getRoom(8,4)
                .setExit(true, false, false, true)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .addItem(ItemFactory.getItem(itemMap.get(4)))
                .setDescription(FOREST);

        getRoom(9,4)
                .setExit(true, true, false, false)
                .setDescription(FOREST);

        // column 5
        getRoom(0,5)
                .setExit(false, true, false, true)
                .setDescription(SNOW);

        getRoom(1,5)
                .setExit(true, false, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(6)))
                .setDescription(SNOW);

        getRoom(2,5)
                .setExit(false, true, true, false)
                .addMonster() // snow.get(3)
                .setDescription(SNOW);

        getRoom(3,5)
                .setExit(false, true, true, false)
                .setDescription(FOREST);

        getRoom(4,5)
                .setExit(false, false, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(2)))
                .addItem(ItemFactory.getItem(itemMap.get(5)))
                .setDescription(RUINS);

        getRoom(5,5)
                .setExit(false, true, true, false)
                .setDescription(FOREST);

        getRoom(6,5)
                .setExit(false, false, true, true)
                .setDescription(RUINS);

        getRoom(7,5)
                .setExit(true, false, false, true)
                .addMonster(ruins.get(0))
                .setDescription(RUINS);

        getRoom(8,5)
                .setExit(true, false, false, true)
                .setDescription(RUINS);

        getRoom(9,5)
                .setExit(true, false, false, false)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .addItem(ItemFactory.getItem(itemMap.get(4)))
                .setDescription(RUINS);

        // column 6
        getRoom(0,6)
                .setExit(false, false, true, true)
                .setDescription(DESERT);

        getRoom(1,6)
                .setExit(true, true, true, false)
                .addMonster( desert.get(0) )
                .setDescription(DESERT);

        getRoom(2,6)
                .setExit(false, true, false, true)
                .setDescription(SNOW);

        getRoom(3,6)
                .setExit(true, true, false, true)
                .setDescription(SNOW);

        getRoom(4,6)
                .setExit(true, true, true, false)
                .setDescription(RUINS);

        getRoom(5,6)
                .setExit(false, true, true, true)
                .setDescription(FOREST);

        getRoom(6,6)
                .setExit(true, true, false, true)
                .setDescription(FOREST);

        getRoom(7,6)
                .setExit(true, false, true, true)
                .addMonster(ruins.get(1))
                .setDescription(RUINS);

        getRoom(8,6)
                .setExit(true, false, false, true)
                .addItem(ItemFactory.getItem(itemMap.get(5)))
                .addItem(ItemFactory.getItem(itemMap.get(9)))
                .setDescription(RUINS);

        getRoom(9,6)
                .setExit(true, false, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .setDescription(RUINS);

        // column 7
        getRoom(0,7)
                .setExit(false, true, true, false)
                .addMonster(desert.get(1))
                .setDescription(DESERT);

        getRoom(1,7)
                .setExit(false, true, false, true)
                .setDescription(DESERT);

        getRoom(2,7)
                .setExit(true, false, false, true)
                .setDescription(DESERT);

        getRoom(3,7)
                .setExit(true, false, false, true)
                .addMonster(desert.get(2))
                .setDescription(DESERT);

        getRoom(4,7)
                .setExit(true, true, true, false)
                .setDescription(RUINS);

        getRoom(5,7)
                .setExit(false, true, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(8)))
                .setDescription(RUINS);

        getRoom(6,7)
                .setExit(false, false, true, true)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .setDescription(RUINS);

        getRoom(7,7)
                .setExit(true, true, true, false)
                .setDescription(RUINS);

        getRoom(8,7)
                .setExit(false, false, true, false)
                .addMonster( ruins.get(2) )
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .setDescription(RUINS);

        getRoom(9,7)
                .setExit(false, true, true, false)
                .setDescription(RUINS);

        // column 8
        getRoom(0,8)
                .setExit(false, true, false, true)
                .setDescription(DESERT);

        getRoom(1,8)
                .setExit(true, false, true, false)
                .setDescription(DESERT);

        getRoom(2,8)
                .setExit(true, false, false, true)
                .addMonster(desert.get(3))
                .setDescription(DESERT);

        getRoom(3,8)
                .setExit(true, false, false, true)
                .setDescription(DESERT);

        getRoom(4,8)
                .setExit(true, true, true, false)
                .setDescription(RUINS);

        getRoom(5,8)
                .setExit(false, true, false, false)
                .addItem(ItemFactory.getItem(itemMap.get(2)))
                .setDescription(RUINS);

        getRoom(6,8)
                .setExit(false, true, true, false)
                .setDescription(RUINS);

        getRoom(7,8)
                .setExit(false, true, true, false)
                .addMonster(ruins.get(3))
                .setDescription(RUINS);

        getRoom(8,8)
                .setExit(false, true, true, true)
                .setDescription(RUINS);

        getRoom(9,8)
                .setExit(true, true, true, false)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .addItem(ItemFactory.getItem(itemMap.get(10)))
                .setDescription(RUINS);

        // column 9
        getRoom(0,9)
                .setExit(false, false, false, true)
                .addMonster( desert.get(4) )
                .addItem(ItemFactory.getItem(itemMap.get(5)))
                .setDescription(DESERT);

        getRoom(1,9)
                .setExit(true, true, false, true)
                .setDescription(DESERT);

        getRoom(2,9)
                .setExit(true, false, false, true)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .addItem(ItemFactory.getItem(itemMap.get(3)))
                .setDescription(DESERT);

        getRoom(3,9)
                .setExit(true, false, false, false)
                .addMonster(desert.get(5))
                .setDescription(DESERT);

        getRoom(4,9)
                .setExit(false, true, false, true )
                .setDescription(RUINS);

        getRoom(5,9)
                .setExit(true, false, false, true)
                .setDescription(RUINS);

        getRoom(6,9)
                .setExit(true, true, false, false)
                .setDescription(RUINS);

        getRoom(7,9)
                .setExit(false, true, false, true)
                .addItem(ItemFactory.getItem(itemMap.get(1)))
                .addItem(ItemFactory.getItem(itemMap.get(7)))
                .setDescription(RUINS);

        getRoom(8,9)
                .setExit(true, true, false, false)
                .setDescription(RUINS);

        getRoom(9,9)
                .setExit(false, true, false, false)
                .setDescription(RUINS);
    }

    @Override
    public Location getStartingLoc() {
        return new Location(9,2);
    }

    @Override
    public int[] getRowColLimit() {
        return new int[]{  };
    }



    private List<Monster> spawnMonster(String region, int number){
        List<Monster> mon = new ArrayList<>();
        Random rand = new Random();
        if (region.equals("desert")){
            for (int i = 0; i < number; i++){
                int rng = rand.nextInt(3);
                switch(rng){
                    case 0:
                        mon.add( new SandTortoise() ); break;
                    case 1:
                        mon.add( new Mummy() ); break;
                    case 2:
                        mon.add( new Coyote() ); break;
                }
            }
        }
        else if (region.equals("grassland")){
            for (int i = 0; i < number; i++){
                int rng = rand.nextInt(6);
                switch(rng){
                    case 0:
                        mon.add( new Slime() ); break;
                    case 1:
                        mon.add( new Goblin() ); break;
                    case 2:
                        mon.add( new Wolves()); break;
                    case 3:
                        mon.add( new Bees()); break;
                    case 4:
                        mon.add( new Deer() ); break;
                    case 5:
                        mon.add( new Mandrake() ); break;
                }
            }
        }
        else if (region.equals("ruins")){
            for (int i = 0; i < number; i++){
                int rng = rand.nextInt(1);
                switch(rng){
                    case 0:
                        mon.add( new Bandits() ); break;
                }
            }
        }
        else if (region.equals("snow")){
            System.out.println("ERROR REGION NOT IMPLEMENTED");
            return null;
        }
        else if (region.equals("swamp")){
            for (int i = 0; i < number; i++){
                int rng = rand.nextInt(4);
                switch(rng){
                    case 0:
                        mon.add( new Cobra()); break;
                    case 1:
                        mon.add( new MudMonster() ); break;
                    case 2:
                        mon.add( new PoisonFrog() ); break;
                    case 3:
                        mon.add( new Slime() ); break;
                }
            }
        }
        else{
            System.out.println("ERROR REGION NOT FOUND");
            return null;
        }
        return mon;
    }
}
