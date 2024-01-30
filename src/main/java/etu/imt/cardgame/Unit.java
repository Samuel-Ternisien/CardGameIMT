package etu.imt.cardgame;

//>> Interface utilisé dans la classe monstre permettant la modification des points de vie
public interface Unit {
    void receiveDamage(int amount);

    void receiveBoost(int amount);

    int getHealth();
}
