package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ItemFactory {

    // List of instance from each valid item
    @Autowired
    public List<Item> REGISTERED_ITEMS;

    // Mapping of Item name to its item instance
    private static Map<String, Item> ITEM_MAP = new HashMap<>();
    private static Map<Integer, Item> ITEM_ID_MAP = new HashMap<>();
    @PostConstruct
    void init(){

        for (Item item : REGISTERED_ITEMS){
            ITEM_MAP.put(item.getName().toLowerCase(Locale.ROOT), item);
            ITEM_ID_MAP.put(item.getItemID(), item);
            System.out.println("LOG: " + item.getName() + " is registered");
        }
    }

    // get Item object that has a name corresponds to the received string
    public static Item getItem(String str){
        return ITEM_MAP.get(str.toLowerCase(Locale.ROOT));
    }

    // get Item object that has an id corresponds to the received string
    public static Item getItem(int id){
        return ITEM_ID_MAP.get(id);
    }

    public static Set<String> getAllItem(){
        return ITEM_MAP.keySet();
    }

}