package etu.imt.cardgame;

import etu.imt.cardgame.Monsters.Monster;

public class Champion implements Unit{
    private static int count = 0;
    private final int id;
    private String name;
    private int health;
    Abilities ability;

    public Champion(int id, String name, int health, Abilities ability) {
        this.id = count++;
        this.name = name;
        this.health = health;
        this.ability = ability;
    }

    @Override
    public void receiveDamage(int amount) {
        this.health -= amount;
    }

    @Override
    public void receiveBoost(int amount) {
        return;
    }

    @Override
    public int getHealth() {
        return this.health;
    }
}
