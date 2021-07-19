package io.upsidedowncubes.multiplayerzork.messageoutput;


import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.JsonConvertor;

import java.util.ArrayList;
import java.util.List;

public class MessageOutput {

    private static StringBuilder sb;
    private static StringBuilder sb_user;
    private static StringBuilder sb_dm;
    private static List<String> dm;

    public static void clear(){
        sb = new StringBuilder();
        sb_user = new StringBuilder();
        sb_dm = new StringBuilder();
        dm = new ArrayList<>();
    }

    // DM List --> [ from who , to whom , message ]
    // if error happen --> list == null
    public static void setSenderReceiver(String from, String to){
        dm.add(from);
        dm.add(to);
    }

    //===============================================
    // single print

    public static void printToAll(String message){
        sb.append(message);
        sb.append("\n");

        sb_user.append(message);
        sb_user.append("\n");

        sb_dm.append(message);
        sb_dm.append("\n");
    }

    public static void printToOthers(String message){
        sb.append(message);
        sb.append("\n");
        //System.out.println(message);
    }

    public static void printToUser(String message){
        sb_user.append(message);
        sb_user.append("\n");
        //System.out.println(message);
    }

    public static void printToDM(String message){
        sb_dm.append(message);
        sb_dm.append("\n");
        //System.out.println(message);
    }

    //===============================================
    // batch print

    public static void printToAll(Iterable<String> messages){
        for (String message : messages){
            printToAll(message);
        }
    }

    public static void printToAll(String[] messages){
        for (String message : messages){
            printToAll(message);
        }
    }

    public static void printToOthers(Iterable<String> messages){
        for (String message : messages){
            printToOthers(message);
        }
    }

    public static void printToDM(Iterable<String> messages){
        for (String message : messages){
            printToDM(message);
        }
    }

    public static void printToUser(Iterable<String> messages){
        for (String message : messages){
            printToUser(message);
        }
    }

    public static void printToOthers(String[] messages){
        for (String message : messages){
            printToOthers(message);
        }
    }

    public static void printToUser(String[] messages){
        for (String message : messages){
            printToUser(message);
        }
    }

    public static void printToDM(String[] messages){
        for (String message : messages){
            printToDM(message);
        }
    }

    //===============================================
    ///*
    public static String getAllOutput(){
        return sb.toString();
    }

    public static String getAllOutput_user(){
         return sb_user.toString();
    }

    public static List<String> getAllOutput_DM(){
        System.out.println("LOG: check dm message=" + sb_dm.toString());
        if ( sb_dm.toString().isBlank() || dm.isEmpty() ){
            return null;
        }

        dm.add( sb_dm.toString() );
        return dm;
    }

     //*/

    /*
    public static String getJsonOutput(){
        return JsonConvertor.convert(sb.toString());
    }

    public static String getJsonOutput_user(){
        return JsonConvertor.convert(sb_user.toString());
    }

     */

}
