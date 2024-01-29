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
        plateau.tourJoueur(champion1);
        plateau.tourJoueur(champion2);
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

    private static void jouerTour(PlateauDeJeu plateau, Champion joueur, Scanner scanner) {
        System.out.println(joueur + ", c'est à votre tour.");


        // Demandez au joueur de jouer une carte
        System.out.println("Voulez-vous jouer une carte ? (oui/non)");
        String reponse = scanner.nextLine();

        if (reponse.equalsIgnoreCase("oui")) {

        }

        // Demandez au joueur d'utiliser sa capacité spéciale
        System.out.println("Voulez-vous utiliser votre capacité spéciale ? (oui/non)");
        reponse = scanner.nextLine();

        if (reponse.equalsIgnoreCase("oui")) {
            joueur.useAbility(plateau.getAdversaire(joueur));
        }

        // Attendez que le joueur confirme la fin de son tour
        System.out.println("Appuyez sur Entrée pour terminer votre tour.");
        scanner.nextLine();
    }
}
