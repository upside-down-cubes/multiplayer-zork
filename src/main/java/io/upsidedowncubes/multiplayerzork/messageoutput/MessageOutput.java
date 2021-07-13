package io.upsidedowncubes.multiplayerzork.messageoutput;


import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.JsonConvertor;

public class MessageOutput {
    // TODO: create another StringBuilder for output that is personalized

    private static StringBuilder sb;
    // private static StingBuilder sb_user;

    public static void init(){
        sb = new StringBuilder();
        // sb_user = new StringBuilder();
    }

    //===============================================
    // single print

    public static void print(String message){
        sb.append(message);
        sb.append("\n");
        //System.out.println(message);
    }

    public static void print_user(String message){
        //sb_user.append(message);
        //sb_user.append("\n");
        //System.out.println(message);
    }

    //===============================================
    // batch print

    public static void print(Iterable<String> messages){
        for (String message : messages){
            print(message);
        }
    }

    public static void print(String[] messages){
        for (String message : messages){
            print(message);
        }
    }

    //===============================================

    public static String getAllOutput(){
        return sb.toString();
    }

    public static String getAllOutput_user(){
        return sb.toString();
        // return sb_user.toString();
    }

    public static String getJsonOutput(){
        return JsonConvertor.convert(sb.toString());
    }

}
