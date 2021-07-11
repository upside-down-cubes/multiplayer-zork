package io.upsidedowncubes.multiplayerzork.messageoutput;

public class MessageOutput {

    public static boolean print(String message){
        System.out.println(message);
        return true;
        // this is temporary implementation
    }

    public static boolean print(Iterable<String> messages){
        for (String message : messages){
            System.out.println(message);
        }
        return true;
        // this is temporary implementation
    }

    public static boolean print(String[] messages){
        for (String message : messages){
            System.out.println(message);
        }
        return true;
        // this is temporary implementation
    }

}
