package etu.imt.cardgame;

import etu.imt.cardgame.Abilities;
import etu.imt.cardgame.Champion;
import etu.imt.cardgame.DeroulementDeJeu.PlateauDeJeu;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Création des champions
        Champion champion1 = new Champion("Pierre", 100, Abilities.Attaque);
        Champion champion2 = new Champion("Samuel", 100, Abilities.Soin);

        // Création du plateau de jeu
        PlateauDeJeu plateau = new PlateauDeJeu(champion1, champion2);

        plateau.debuterPartie();
/*
        for (int i = 0; i < 5; i++) {
            System.out.println("Tour " + (i + 1));

            // Tour du joueur 1
            jouerTour(plateau, champion1, scanner);

            // Tour du joueur 2
            jouerTour(plateau, champion2, scanner);
        }

        plateau.finDePartie();

        scanner.close();
 */
    }
}
