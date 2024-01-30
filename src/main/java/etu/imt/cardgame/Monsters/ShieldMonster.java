package etu.imt.cardgame.Monsters;

import etu.imt.cardgame.DeroulementDeJeu.PlateauDeJeu;
import etu.imt.cardgame.Unit;

public class ShieldMonster extends Monster {
    public ShieldMonster(String name, int health, int power) {
        super(name, health, power);
    }

    @Override
    public void targetUnit(Unit target) {
        PlateauDeJeu.logConsoleAndFile("Le monstre bouclier n'arrive pas à attaquer.");
    }
}
