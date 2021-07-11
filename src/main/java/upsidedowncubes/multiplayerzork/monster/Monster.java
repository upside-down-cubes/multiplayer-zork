package upsidedowncubes.multiplayerzork.monster;


import java.util.Random;

public abstract class Monster {

    String name;
    int hp;
    int maxHP;
    int atk;

    // TODO: Implement drop system (will add into room's item list)

    Random rand = new Random();

    public int getHP() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public abstract void act();

    public void receiveDamage(int amount){
        hp -= amount;
    }

    public boolean isDead(){
        return hp <= 0;
    }

    public void gainHP(int amount){
        int amountHealed;
        if (hp + amount > maxHP){
            amountHealed = maxHP - hp;
        }
        else{
            amountHealed = amount;
        }
        System.out.println(getName() + " gained " + amountHealed + " HP");
        hp += amountHealed;
    }

}
