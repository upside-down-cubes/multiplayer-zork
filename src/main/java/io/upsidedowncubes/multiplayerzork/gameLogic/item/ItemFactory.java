package io.upsidedowncubes.multiplayerzork.gameLogic.item;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

public class ItemFactory {

    // List of instance from each valid item
    @Autowired
    public List<Item> REGISTERED_ITEMS;

    // Mapping of Item name to its item instance
    private static Map<String, Item> ITEM_MAP = new HashMap<>();
    @PostConstruct
    void init(){

        for (Item item : REGISTERED_ITEMS){
            ITEM_MAP.put(item.getName().toLowerCase(Locale.ROOT), item);
        }
    }

    // get Item object that corresponds to the received string
    public static Item getItem(String str){
        return ITEM_MAP.get(str.toLowerCase(Locale.ROOT));
    }

}