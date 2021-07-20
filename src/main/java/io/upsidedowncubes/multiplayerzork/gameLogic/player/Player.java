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

    private String username;
    private int hp;
    private int maxHP;

    private int atk;

    private final double missRate = 0.1;
    private final double critRate = 0.1;
    private final double critMultiplier = 1.5;
    private final Random rand = new Random();

    private Inventory bag;
    private Location currentLoc;

    public Player(String username){

        PlayerEntity player = PlayerRepositoryHelper.getPlayerEntity(username);

        this.username = username;
        this.hp = player.getHp();
        this.maxHP = player.getMaxHp();
        this.atk = player.getAttack();

        bag = new Inventory(username);
        currentLoc = new Location(username);
    }

    public Room getCurrentRoom() {
        Game game = OurWebSocketHandler.getGameByUser(username);
        GameMap gm = game.getMap();
        return gm.getRoom( currentLoc.getRow(), currentLoc.getCol() );
    }

    public Location getCurrentLoc(){
        return currentLoc;
    }

    public String getUsername(){
        return username;
    }

    public int getHp(){
        return hp;
    }

    public int getMaxHP(){
        return maxHP;
    }

    public void gainMaxHP(int amount){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);
        messageOut.printToUser( "You MaxHP is increased by " + amount );
        maxHP += amount;

    }

    public int gainHP(int amount){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        int amountHealed;
        if (hp + amount > maxHP){
            amountHealed = maxHP - hp;
        }
        else{
            amountHealed = amount;
        }

        messageOut.printToUser( "You gained " + amountHealed + " HP");
        hp += amountHealed;
        return amountHealed;
    }

    public void loseHP(int amount){
        hp -= amount;
    }

    public boolean isFullHP(){ return hp == maxHP; }

    public Inventory getBag(){ return bag; }

    public void viewStatus(){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);
        messageOut.printToUser("==== Player Information: " + username + " ====");
        messageOut.printToUser("HP: " + hp + "/" + maxHP);
        messageOut.printToUser("ATK: " + atk);
        messageOut.printToUser("============================");
    }

    public int attack(Weapon wp){
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);
        int damage;

        // determines if the attack lands
        if (attackMiss()){
            messageOut.printToUser( "Your attack miss...");
            messageOut.printToOthers( "[ " + username + " ] 's attack misses...");
            damage = -1;
        }
        else{
            // determines the damage when the attack lands
            double damageMultiplier = 1.0;
            if (performCrit()){
                damageMultiplier = getCritMultiplier();
                messageOut.printToAll("A Critical Hit!!!");
            }
            damage = (int) Math.round( getATK(wp) * damageMultiplier );
        }
        return damage;
    }

    public int getATK( Weapon wp ){
        if (wp == null){
            return atk;
        }
        return atk + wp.getAttackStat();
    }

    public boolean performCrit(){
        return rand.nextDouble() < critRate;
    }

    public boolean attackMiss(){
        return rand.nextDouble() < missRate;
    }

    public double getCritMultiplier() {
        return critMultiplier;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public Direction move(Direction dir) {
        MessageOutput messageOut = MessageCenter.getUserMessageOut(username);

        Room r = getCurrentRoom();
        // check if can move there
        if (! r.getAvailableExit().contains(dir) ){
            return null;
        }

        // if can move, change currentLocation to be the location in that direction
        switch (dir){
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

}
