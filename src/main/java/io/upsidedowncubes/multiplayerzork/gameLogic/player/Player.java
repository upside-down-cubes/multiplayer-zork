package io.upsidedowncubes.multiplayerzork.gameLogic.player;

import io.upsidedowncubes.multiplayerzork.gameLogic.Game;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Inventory;
import io.upsidedowncubes.multiplayerzork.gameLogic.item.Weapon;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Direction;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.GameMap;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Location;
import io.upsidedowncubes.multiplayerzork.gameLogic.map.Room;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageCenter;
import io.upsidedowncubes.multiplayerzork.messageoutput.MessageOutput;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.webSocket.OurWebSocketHandler;

import java.util.Random;

public class Player {

    private final String username;
    private int hp;
    private final int maxHP;
    private final int atk;
    private final int exp;
    private final int maxExp;

    private final double missRate = 0.1;
    private double critRate = 0.1;
    private double critMultiplier = 1.5;

    private final Random rand = new Random();

    private final Inventory bag;
    private final Location currentLoc;

    public Player(String username) {

        PlayerEntity player = PlayerRepositoryHelper.getPlayerEntity(username);

        this.username = username;
        this.hp = player.getHp();
        this.maxHP = player.getMaxHp();
        this.atk = player.getAttack();

        this.exp = player.getExp();
        this.maxExp = player.getMaxExp();

        bag = new Inventory(username);
        currentLoc = new Location(username);
    }

    // getter ==========================================================

    public int getHp() {
        return hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public boolean isFullHP() {
        return hp == maxHP;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public String getUsername() {
        return username;
    }

    public Room getCurrentRoom() {
        Game game = OurWebSocketHandler.getGameByUser(username);
        GameMap gm = game.getMap();
        return gm.getRoom(currentLoc.getRow(), currentLoc.getCol());
    }

    public Location getCurrentLoc() {
        return currentLoc;
    }

    public Inventory getBag() {
        return bag;
    }

    public double getCritMultiplier() {
        return critMultiplier;
    }

    // setter ==========================================================

    public int gainHP(int amount) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        int amountHealed;
        if (hp + amount > maxHP) {
            amountHealed = maxHP - hp;
        } else {
            amountHealed = amount;
        }

        messageOut.printToUser("You gained " + amountHealed + " HP");
        hp += amountHealed;
        return amountHealed;
    }

    public void loseHP(int amount) {
        hp -= amount;
    }

    public void increaseCritRate(double amount) {
        critRate += amount;
    }

    public void increaseCritMultiplier(double amount) {
        critMultiplier += amount;
    }


    // action ==========================================================

    public boolean performCrit() {
        return rand.nextDouble() < critRate;
    }

    public boolean attackMiss() {
        return rand.nextDouble() < missRate;
    }

    public int attack(Weapon wp) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);
        int damage;

        if (wp != null) {
            wp.passiveEffect(this);
        }

        // determines if the attack lands
        if (attackMiss()) {
            messageOut.printToUser("Your attack miss...");
            messageOut.printToOthers("[ " + username + " ] 's attack misses...");
            damage = -1;
        } else {
            // determines the damage when the attack lands
            double damageMultiplier = 1.0;
            if (performCrit()) {
                damageMultiplier = getCritMultiplier();
                messageOut.printToAll("A Critical Hit!!!");
            }

            int wpAtk;
            if (wp == null) {
                wpAtk = 0;
            } else {
                wpAtk = wp.getAttackStat(this);
            }
            damage = (int) Math.round((wpAtk + atk) * damageMultiplier);

            if (wp != null) {
                wp.activeEffect(this);
            }
        }
        return damage;
    }

    public Direction move(Direction dir) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        Room r = getCurrentRoom();
        // check if can move there
        if (!r.getAvailableExit().contains(dir)) {
            return null;
        }

        // if can move, change currentLocation to be the location in that direction
        switch (dir) {
            case N:
                currentLoc.goNorth();
                messageOut.printToUser("You proceeded to the North");
                break;
            case E:
                currentLoc.goEast();
                messageOut.printToUser("You proceeded to the East");
                break;
            case W:
                currentLoc.goWest();
                messageOut.printToUser("You proceeded to the West");
                break;
            case S:
                currentLoc.goSouth();
                messageOut.printToUser("You proceeded to the South");
                break;
        }
        return dir;
    }


//    public void viewStatus(){
//        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);
//        messageOut.printToUser("==== Player Information: " + username + " ====");
//        messageOut.printToUser("HP: " + hp + "/" + maxHP);
//        messageOut.printToUser("ATK: " + atk);
//        messageOut.printToUser("============================");
//    }

}
