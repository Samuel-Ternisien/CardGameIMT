package etu.imt.cardgame.DeroulementDeJeu;

import etu.imt.cardgame.Champion;
import etu.imt.cardgame.Monsters.Monster;
import etu.imt.cardgame.Monsters.MonsterBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//>> Création du plateau de jeu
public class PlateauDeJeu {
    final private Champion joueur1;
    final private Champion joueur2;
    private static List<Monster> cemetery;
    private static final Logger log = Logger.getLogger("cardGame");

    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<Monster> attackUsed = new ArrayList<>();

    public PlateauDeJeu(Champion joueur1, Champion joueur2) throws IOException {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        InputStream fis =  new FileInputStream("src/main/java/resources/log.properties");
        LogManager.getLogManager().readConfiguration(fis);
    }

    //>> Initialisation de la partie
    public void debuterPartie() throws Exception {
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
        tourJoueur(joueur1, joueur2);
    }

    //>> Logique de déroulement d'un tour
    public void tourJoueur(Champion joueur, Champion target) throws Exception {
        // Tour d'un joueur
        // Réinitialise les units qui ont attacké
        attackUsed.clear();
        Monster choice = drawRandomMonster();
        log.info(String.format("Le joueur %s commence son tour et pioche: %s", joueur.getName(), choice.getName()));
        System.out.printf("Joueur %s commence son tour:%n", joueur.getName());
        // On présente le menu au joueur et une fois qu'il sort du menu on vérifie que la partie ne soit pas fini
        menuTour(joueur, target);
        if (!finDePartie()) {
            // le joueur actuel devient cible et la cible devient le joueur
            tourJoueur(target, joueur);
        }
        else{
            System.out.println("La partie est finie");
        }
    }

    //>> Menu avec les options dipsonibles lors d'un tour
    public void menuTour(Champion joueur, Champion target) throws Exception {
        boolean usedCard = false,usedAbility = false;
        int choiceMenu;
        do {
            if (finDePartie()){
                System.out.println("La partie est finie");
                return;
            }
            System.out.println("Que voulez vous faire ? \n 1. Jouer une carte. \n 2. Voir votre main. \n 3. Voir les cartes sur le plateau. \n 4. Attaquer. \n 5. Activer la capacité spéciale du champion. \n 6. Finir votre tour.");
            choiceMenu = scanner.nextInt();
            switch (choiceMenu) {
                case 1:
                    if (usedCard) {
                        System.out.println("Vous avez déjà posé une carte, impossible de reposser");
                        break;
                    }
                    usedCard = true;
                    System.out.println("Choisissez l'id d'une carte à poser sur le plateau");
                    for (Monster m : joueur.getDeck()) {
                        System.out.printf("ID: %s, Nom:%s,%n", m.getId(), m.getName());
                    }
                    joueur.jouerCarte(scanner.nextInt());
                    logConsoleAndFile(String.format("La carte %s a été posée%n", joueur.getOnBoard().get(joueur.getOnBoard().size() - 1).getName()));
                    break;
                case 2:
                    System.out.println("Les monstres de votre main");
                    joueur.getDeck().forEach((monster -> System.out.println(" | Nom : " + monster.getName() + " | Santé : " + monster.getHealth() + " | Attaque " + monster.getPower() + " | ")));
                    break;
                case 3:
                    System.out.println("Voir les monstres sur le plateau");
                    System.out.printf("Les monstres de %s avec: %s PV: %n", joueur.getName(), joueur.getHealth());
                    if (!joueur.getOnBoard().isEmpty())
                        joueur.getOnBoard().forEach((monster -> System.out.println(" | ID :" + monster.getId() + "| Nom : " + monster.getName() + " | Santé : " + monster.getHealth() + " | Attaque : " + monster.getPower())));
                    System.out.printf("Les monstres de %s avec: %s PV: %n", target.getName(), target.getHealth());
                    if (!target.getOnBoard().isEmpty())
                        target.getOnBoard().forEach((monster -> System.out.println(" | ID :" + monster.getId() + "| Nom : " + monster.getName() + " | Santé : " + monster.getHealth() + " | Attaque : " + monster.getPower())));
                    break;
                case 4:
                    attackTurn(joueur, target);
                    break;
                case 5:
                    if (usedAbility) {
                        System.out.println("Vous avez déjà utilisé votre compétence, impossible de la réutiliser");
                        break;
                    }
                    usedAbility = true;
                    // TODO: choose target and log usage 
                    joueur.useAbility(target);
                    break;
                case 6:
                    System.out.println("Finir votre tour");
                    break;
                default:
                    System.out.println("Choix invalide");
                    break;

            }
        }while(choiceMenu!=6);
    }

    //>> Vérifie les points de vie des joueurs pour mettre fin à la partie
    public boolean finDePartie() {
        return joueur1.getHealth() <= 0 || joueur2.getHealth() <= 0 ;
    }

    //>> Pioche au début du tour, génère aléatoirement un monstre de la liste
    public Monster drawRandomMonster(){
        Random random = new Random();
        int choice = random.nextInt(5);
        return switch (choice) {
            case 0 -> MonsterBuilder.buildMonster("Healer");
            case 1 -> MonsterBuilder.buildMonster("Necro");
            case 2 -> MonsterBuilder.buildMonster("Shield");
            case 4 -> MonsterBuilder.buildMonster("Support");
            default -> MonsterBuilder.buildMonster("Simple");
        };
    }

    //>> Système d'attaque permettant de sélectionner un monstre via son ID
    public void attackTurn(Champion attaquant, Champion target) throws Exception {
        ArrayList<Integer> idAvailable = new ArrayList<>();
        System.out.println("Attaquer");
        System.out.println("Voici vos monstres disponible à l'attaque:");
        attaquant.getOnBoard().forEach((monster -> {
            if (!attackUsed.contains(monster)) {
                System.out.printf("ID: %s,Nom: %s, PV: %s, CP: %s %n", monster.getID(), monster.getName(), monster.getHealth(), monster.getPower());
                idAvailable.add(monster.getID());
            }
            }));
        int choice;
        do{
            System.out.printf("Avec quel monstre voulez-vous attaquer? %nPour sortir du menu attaquer, entrer 999.%n");
            choice = scanner.nextInt();
            if (choice==999) break;
            if (!idAvailable.contains(choice)){
                System.out.println("Id non valide!");
                break;
            }
            idAvailable.remove((Integer)choice);
            attackUsed.add(attaquant.getBoardMonsterById(choice));
            menuTarget(target);
            int targetInt = scanner.nextInt();
            if (targetInt==0){
                Monster isShielded = target.getShield();
                if(isShielded!=null){
                    logConsoleAndFile("Le champion posséde un monstre bouclier sur son terrain, il se sacrifie pour son maître!");
                    attaquant.getBoardMonsterById(choice).targetUnit(isShielded);
                    String msg = String.format("Le monstre %s attaque le monstre %s et lui inflige %s dégats. Il lui reste maintenant %s PVs",
                            attaquant.getBoardMonsterById(choice).getName(),
                            isShielded.getName(),
                            attaquant.getBoardMonsterById(choice).getPower(),
                            isShielded.getHealth()
                    );
                    logConsoleAndFile(msg);
                    break;
                }
                attaquant.getBoardMonsterById(choice).targetUnit(target);
                String msg = String.format("Le monstre %s attaque le champion et lui inflige %s dégats. Il lui reste maintenant %s PVs",
                        attaquant.getBoardMonsterById(choice).getName(),
                        attaquant.getBoardMonsterById(choice).getPower(),
                        target.getHealth()
                );
                logConsoleAndFile(msg);
                break;
            }
            if (target.getBoardMonsterById(targetInt)!=null){
                attaquant.getBoardMonsterById(choice).targetUnit(target.getBoardMonsterById(targetInt));
                String msg = String.format("Le monstre %s attaque le monstre %s et lui inflige %s dégats. Il lui reste maintenant %s PVs",
                        attaquant.getBoardMonsterById(choice).getName(),
                        target.getBoardMonsterById(targetInt).getName(),
                        attaquant.getBoardMonsterById(choice).getPower(),
                        target.getBoardMonsterById(targetInt).getHealth()
                );
                logConsoleAndFile(msg);
            }
        }while ((choice!=999));
        checkDeadMonster(target, attaquant);
    }

    public static void checkDeadMonster(Champion c1, Champion c2) {
        // On récupére la liste des monstres mort de chaque joueur pour la concatener dans 1 seul liste
        List<Monster> allDead = Stream
                .concat(c1.getDeadMonsters().stream(),
                        c2.getDeadMonsters().stream())
                .collect(Collectors.toList());
        if(!allDead.isEmpty()){
            logConsoleAndFile("Les monstres suivants sont morts et sont ajoutés au cimetière:");
            allDead.forEach(monster -> logConsoleAndFile(String.format("Nom: %s%n", monster.getName())));
            cemetery.addAll(allDead);
        }
    }

    public static void menuTarget(Champion target){
        System.out.println("Voici le plateau de votre adversaire:");
        System.out.printf("Champion: Nom: %s, PV: %s%n", target.getName(), target.getHealth());
        System.out.println("Monstres:");
        target.getOnBoard().forEach((monster -> System.out.printf("ID: %s,Nom: %s, PV: %s, CP: %s %n",monster.getID(), monster.getName(), monster.getHealth(),monster.getPower())));
        System.out.println("Quel monstre voulez-vous attaquer? Pour sélectionner le Champion adverse, entrer 0");
    }

    public static void logConsoleAndFile(String msg){
        System.out.println(msg);
        log.info(msg);
    }
}
