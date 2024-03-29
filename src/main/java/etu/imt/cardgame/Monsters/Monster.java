package etu.imt.cardgame.Monsters;

import etu.imt.cardgame.Unit;

//>> Définition de la classe monstre
public abstract class Monster implements Unit {
    private static int count = 0;
    private final int id;
    private String name;
    private int health;
    private int power;

    public Monster(String name, int health, int power) {
        id = ++count;
        this.name = name + "_" + id;
        this.health = health;
        this.power = power;
    }

    // Getter and setter for every usefull variable
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public void receiveDamage(int amount) {
        this.health -= amount;
    }

    @Override
    public void receiveBoost(int amount) {
        this.power += amount;
    }

    public void targetUnit(Unit target) throws Exception {
        target.receiveDamage(this.power);
    }
}
