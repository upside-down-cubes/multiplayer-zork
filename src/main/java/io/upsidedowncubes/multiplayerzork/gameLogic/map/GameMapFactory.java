package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameMapFactory {

    public static final List<Class<? extends GameMap>> REGISTERED_MAP = Arrays.asList(
            TestGameMap.class,
            KingdomOfRuinsMap.class
    );


    private static Map<String, Class<? extends GameMap>> GAMEMAP_MAP = new HashMap<>();
    static {{
        for ( Class<? extends GameMap> gmClass : REGISTERED_MAP ){
            try {
                GameMap gm = gmClass.getDeclaredConstructor().newInstance();
                GAMEMAP_MAP.put(gm.getMapName(), gmClass);
            }
            catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }}

    public static GameMap getMap(String str){

        try {
            GameMap gm = GAMEMAP_MAP.get(str).getDeclaredConstructor().newInstance();
            return gm;
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getAllMapName(){
        StringBuilder sb = new StringBuilder();
        sb.append("Available map: [ ");
        for (String name : GAMEMAP_MAP.keySet()){
            sb.append(" " + name + " ");
        }
        sb.append(" ]");
        return sb.toString();
    }
}
