package upsidedowncubes.multiplayerzork.gameLogic.player;

import upsidedowncubes.multiplayerzork.gameLogic.output.MessageOutput;

import java.util.Random;

public class Player {

    private int hp;
    private int maxHP;

    private int atk;

    private double missRate;
    private double critRate;
    private double critMultiplier;
    private Random rand = new Random();


    public Player(){
        hp = 50;
        maxHP = 50;
        atk = 5;

        missRate = 0.1;
        critRate = 0.1;
        critMultiplier = 1.5;
    }

    public void viewStatus(){
        MessageOutput.print("==== Player Information ====");
        MessageOutput.print("HP: " + hp + "/" +maxHP);
        MessageOutput.print("ATK: " + atk);
        MessageOutput.print("============================");
    }

}
