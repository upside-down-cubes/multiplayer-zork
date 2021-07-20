package io.upsidedowncubes.multiplayerzork.messageoutput;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageCenter {

    private static Map<String, MessageOutput> msgCenter;

    @PostConstruct
    void init(){
        msgCenter = new HashMap<>();
    }

    public static void addUser(String username){
        msgCenter.put(username, new MessageOutput(username));
    }

    public static void removeUser(String username){
        msgCenter.remove(username);
    }

    public static MessageOutput getUserMessageOut(String username){
        return msgCenter.get(username);
    }

}
