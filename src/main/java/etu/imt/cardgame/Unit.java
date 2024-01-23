package etu.imt.cardgame;

public interface Unit {
    void receiveDamage(int amount);
    void receiveBoost(int amount);
    int getHealth();
}
