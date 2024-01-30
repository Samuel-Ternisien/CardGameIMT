package etu.imt.cardgame;

import etu.imt.cardgame.Abilities;
import etu.imt.cardgame.Champion;
import etu.imt.cardgame.DeroulementDeJeu.PlateauDeJeu;

import java.io.IOException;
import java.util.Scanner;
/** @author Pierre VANDENBERGHE et Samuel TERNISIEN **/
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("---- Bienvenue dans la partie de jeu, ce programme vous est proposé par VANDENBERGHE Pierre et TERNISIEN Samuel en FISA-2026");

        // Création des champions
        Champion champion1 = new Champion("Atlas Borne", 60, Abilities.Attaque);
        Champion champion2 = new Champion("Glemma Rouss", 50, Abilities.Soin);

        // Création du plateau de jeu
        PlateauDeJeu plateau = new PlateauDeJeu(champion1, champion2);

        // Lancement de la partie
        plateau.debuterPartie();
    }
}
