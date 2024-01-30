package etu.imt.cardgame;

import etu.imt.cardgame.Abilities;
import etu.imt.cardgame.Champion;
import etu.imt.cardgame.DeroulementDeJeu.PlateauDeJeu;

import java.io.IOException;
import java.util.Scanner;
/** @author Pierre VANDENBERGHE et Samuel TERNISIEN **/
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Création des champions
        Champion champion1 = new Champion("Pierre", 100, Abilities.Attaque);
        Champion champion2 = new Champion("Samuel", 100, Abilities.Soin);

        // Création du plateau de jeu
        PlateauDeJeu plateau = new PlateauDeJeu(champion1, champion2);

        // Lancement de la partie
        plateau.debuterPartie();
    }

    //handlers = java.util.logging.FileHandler;
}
