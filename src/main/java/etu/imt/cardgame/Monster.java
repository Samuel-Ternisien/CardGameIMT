package etu.imt.cardgame;

public class Monster implements Unit{
    private static int count = 0;
    private int id;
    private String name;
    private int health;
    private int power;

    public Monster(String name, int health, int power) {
        id = ++count;
        this.name = name;
        this.health = health;
        this.power = power;
    }

    @Override
    public void receiveDamage(int amount) {
        this.health -= amount;
    }

    public int getHealth() {
        return health;
    }

    public void attack(Unit target){
        target.receiveDamage(this.power);
    }
}
