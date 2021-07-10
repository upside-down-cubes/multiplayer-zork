package upsidedowncubes.multiplayerzork.player;

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
        System.out.println("==== Player Information ====");
        System.out.println("HP: " + hp + "/" +maxHP);
        System.out.println("ATK: " + atk);
        System.out.println("============================");
    }

}
