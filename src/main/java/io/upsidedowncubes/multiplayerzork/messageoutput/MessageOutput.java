package io.upsidedowncubes.multiplayerzork.messageoutput;


import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.JsonConvertor;

public class MessageOutput {

    private static StringBuilder sb;
    private static StringBuilder sb_user;

    public static void clear(){
        sb = new StringBuilder();
        sb_user = new StringBuilder();
    }

    //===============================================
    // single print

    public static void printToAll(String message){
        sb.append(message);
        sb.append("\n");

        sb_user.append(message);
        sb_user.append("\n");
        //System.out.println(message);
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

    //===============================================
    ///*
    public static String getAllOutput(){
        return sb.toString();
    }

    public static String getAllOutput_user(){
         return sb_user.toString();
    }

     //*/

    public static String getJsonOutput(){
        return JsonConvertor.convert(sb.toString());
    }

    public static String getJsonOutput_user(){
        return JsonConvertor.convert(sb_user.toString());
    }

}
