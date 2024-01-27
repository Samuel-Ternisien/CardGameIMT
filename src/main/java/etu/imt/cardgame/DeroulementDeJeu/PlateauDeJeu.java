package etu.imt.cardgame.DeroulementDeJeu;

import etu.imt.cardgame.Champion;
import etu.imt.cardgame.Monsters.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlateauDeJeu {
    private Champion joueur1;
    private Champion joueur2;
    private List<Monster> monstresSurPlateau;

    public PlateauDeJeu(Champion joueur1, Champion joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.monstresSurPlateau = new ArrayList<>();
    }

    public void debuterPartie() {
        // Initialisation de la partie
    }

    public void tourJoueur(Champion joueur) {
        // Tour d'un joueur
        joueur.jouerCarte();
        joueur.useAbility(joueur2);
        attaquerAvecMonstres(joueur);
    }

    public Champion getAdversaire(Champion champion) {
        return (champion.equals(joueur1)) ? joueur2 : joueur1;
    }

    private void attaquerAvecMonstres(Champion joueur) {
        for (Monster monstre : monstresSurPlateau) {
            if (!monstre.aAttenduUnTour()) {
                monstre.attaquer(joueur2);
                monstre.attendreTour();
            }
        }
    }

    public void tourSuivant() {
        tourJoueur(joueur1);
        tourJoueur(joueur2);
    }

    public void finDePartie() {

    }
}
