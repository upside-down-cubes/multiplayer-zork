package io.upsidedowncubes.multiplayerzork.messageoutput;


public class MessageOutput {

    private static StringBuilder sb;

    public static void init(){
        sb = new StringBuilder();
    }

    public static void print(String message){
        sb.append(message);
        sb.append("\n");
        //System.out.println(message);
    }

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

    public static String getAllOutput(){
        return sb.toString();
    }

}
