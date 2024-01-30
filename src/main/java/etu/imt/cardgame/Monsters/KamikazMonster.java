package etu.imt.cardgame.Monsters;

import etu.imt.cardgame.DeroulementDeJeu.PlateauDeJeu;
import etu.imt.cardgame.Unit;

public class KamikazMonster extends Monster{
    public KamikazMonster(String name, int health, int power) {
        super(name, health, power);
    }

    @Override
    public void targetUnit(Unit target) throws Exception {
        PlateauDeJeu.logConsoleAndFile("Kabooom! Le kamikaze explose");
        target.receiveDamage(this.getPower()*100);
        this.setHealth(0);
    }
}
