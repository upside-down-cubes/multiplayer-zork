package io.upsidedowncubes.multiplayerzork.messageoutput;

import java.util.ArrayList;
import java.util.List;

public class MessageOutput {

    private StringBuilder sb;
    private StringBuilder sb_user;
    private StringBuilder sb_dm;
    private List<String> dm;

    public MessageOutput() {
        sb = new StringBuilder();
        sb_user = new StringBuilder();
        sb_dm = new StringBuilder();
        dm = new ArrayList<>();
    }


    //==============================================================================================


    public void clear() {
        sb = new StringBuilder();
        sb_user = new StringBuilder();
        sb_dm = new StringBuilder();
        dm = new ArrayList<>();
    }

    // DM List --> [ to whom , message ]
    // if error happen --> list == null
    public void setSenderReceiver(String from, String to) {
        dm.add(from);
        dm.add(to);
    }

    //===============================================
    // single print

    public void printToAll(String message) {
        sb.append(message);
        sb.append("\n");

        sb_user.append(message);
        sb_user.append("\n");

        sb_dm.append(message);
        sb_dm.append("\n");
    }

    public void printToOthers(String message) {
        sb.append(message);
        sb.append("\n");
        //System.out.println(message);
    }

    public void printToUser(String message) {
        sb_user.append(message);
        sb_user.append("\n");
        //System.out.println(message);
    }

    public void printToDM(String message) {
        sb_dm.append(message);
        sb_dm.append("\n");
        //System.out.println(message);
    }

    //===============================================
    // batch print

    public void printToAll(Iterable<String> messages) {
        for (String message : messages) {
            printToAll(message);
        }
    }

    public void printToAll(String[] messages) {
        for (String message : messages) {
            printToAll(message);
        }
    }

    public void printToOthers(Iterable<String> messages) {
        for (String message : messages) {
            printToOthers(message);
        }
    }

    public void printToDM(Iterable<String> messages) {
        for (String message : messages) {
            printToDM(message);
        }
    }

    public void printToUser(Iterable<String> messages) {
        for (String message : messages) {
            printToUser(message);
        }
    }

    public void printToOthers(String[] messages) {
        for (String message : messages) {
            printToOthers(message);
        }
    }

    public void printToUser(String[] messages) {
        for (String message : messages) {
            printToUser(message);
        }
    }

    public void printToDM(String[] messages) {
        for (String message : messages) {
            printToDM(message);
        }
    }

    //===============================================
    ///*
    public String getAllOutput() {
        return sb.toString();
    }

    public String getAllOutput_user() {
        return sb_user.toString();
    }

    public List<String> getAllOutput_DM() {
        if (sb_dm.toString().isBlank() || dm.isEmpty()) {
            return null;
        }

        dm.add(sb_dm.toString());
        return dm;
    }

}
