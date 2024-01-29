package etu.imt.cardgame.DeroulementDeJeu;

import etu.imt.cardgame.Champion;
import etu.imt.cardgame.Monsters.Monster;
import etu.imt.cardgame.Monsters.MonsterBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.Scanner;

public class PlateauDeJeu {
    private Champion joueur1;
    private Champion joueur2;
    private List<Monster> cemetery;
    private Logger log;
    private Scanner scanner = new Scanner(System.in);

    public PlateauDeJeu(Champion joueur1, Champion joueur2) throws IOException {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        FileHandler fh = new FileHandler("./log.txt");
        log = Logger.getLogger("imt.cardGame.board");
        log.addHandler(fh);
    }

    public void debuterPartie() {
        Monster choice;
        log.info(String.format("La partie débute avec les joueurs: %s contre %s", joueur1, joueur2));
        for (int i = 0; i <= 5; i++) {
            choice = drawRandomMonster();
            log.info(String.format("Le joueur %s à tiré la carte: %s", joueur1.getName(), choice.getName()));
            joueur1.addMonster(choice);
        }

        for (int i = 0; i <= 5; i++) {
            choice = drawRandomMonster();
            log.info(String.format("Le joueur %s à tiré la carte: %s", joueur2.getName(), choice.getName()));
            joueur2.addMonster(choice);
        }

    }

    public void tourJoueur(Champion joueur) {
        // Tour d'un joueur
        Monster choice = drawRandomMonster();
        log.info(String.format("Le joueur %s commence son tour et pioche: %s", joueur.getName(), choice.getName()));
        System.out.println(String.format("Joueur %s commence son tour:", joueur.getName()));

        if(joueur.getOnBoard().size()>4){
            log.info("Le joueur à déjà trop de carte sur le plateau, il ne peut pas poser plus de carte");
        }
        else{
            System.out.println("Vous pouvez poser les cartes:");
            for (Monster m: joueur.getDeck()) {
                System.out.println(String.format("ID: %s, Nom:%s,",m.getId(),m.getName()));
            }

        }
        menuTour(joueur);

        joueur.useAbility(joueur2);
        attaquerAvecMonstres(joueur);
    }

    public void menuTour (Champion joueur) {
        System.out.println("Que voulez vous faire ? \n 1. Jouer une carte. \n 2. Voir votre main. \n 3. Voir les cartes sur le plateau. \n 4. Attaquer. \n 5. Finir votre tour.");
        int choiceMenu = scanner.nextInt();
        switch (choiceMenu) {
            case 1:
                System.out.println("Choisissez l'id d'une carte à poser sur le plateau");
                joueur.jouerCarte(scanner.nextInt());
                System.out.println(String.format("La carte %s a été posée",joueur.getOnBoard().get(joueur.getOnBoard().size()-1).getName()));
                break;
            case 2:
                System.out.println("Les monstres de votre main");
                joueur.getDeck().forEach((monster -> System.out.println(monster.getName())));
                break;
            case 3:
                System.out.println("Voir les monstres sur le plateau");
                if joueur.getOnBoard().isEmpty() {
                    System.out.println("Le plateau est vide");
                }

                joueur.getOnBoard().forEach((monster -> System.out.println(monster.getName())));
                break;
            case 4:
                System.out.println("Attaquer");
                break;
            case 5:
                System.out.println("Finir votre tour");
                break;
            default:
                System.out.println("Choix invalide");
                break;

        }
        menuTour(joueur);
    }

    public Champion getAdversaire(Champion champion) {
        return (champion.equals(joueur1)) ? joueur2 : joueur1;
    }

    private void attaquerAvecMonstres(Champion joueur) {
        for (Monster monstre : joueur.getDeck()) {
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

    public boolean finDePartie() {
        return joueur1.getHealth() <= 0 || joueur2.getHealth() <= 0 ;
    }

    public Monster drawRandomMonster(){
        Random random = new Random();
        int choice = random.nextInt(0,4);
        switch (choice) {
            case 0:
                return MonsterBuilder.buildMonster("Healer");
            case 1:
                return MonsterBuilder.buildMonster("Necro");
            case 2:
                return MonsterBuilder.buildMonster("Shield");
            case 3:
                return MonsterBuilder.buildMonster("Simple");
            case 4:
                return MonsterBuilder.buildMonster("Support");
            default:
                return MonsterBuilder.buildMonster("Simple");
        }
    }


}
