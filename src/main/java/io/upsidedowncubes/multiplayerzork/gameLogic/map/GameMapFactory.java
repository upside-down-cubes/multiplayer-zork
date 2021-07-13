package io.upsidedowncubes.multiplayerzork.gameLogic.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class GameMapFactory {

    @Autowired
    public List<GameMap> REGISTERED_MAP;

    private static Map<String, GameMap> GAMEMAP_MAP = new HashMap<>();
    private static List<String> GAMEMAP_LIST = new ArrayList<>();
    @PostConstruct
    void init(){
        for (GameMap gm : REGISTERED_MAP ){
            GAMEMAP_MAP.put(gm.getMapName(), gm);
            GAMEMAP_LIST.add(gm.getMapName());
        }
    }


    public static GameMap getMap(String str){
        return GAMEMAP_MAP.get(str);
    }

    public static List<String> getAllMapName(){
        return GAMEMAP_LIST;
    }
}
