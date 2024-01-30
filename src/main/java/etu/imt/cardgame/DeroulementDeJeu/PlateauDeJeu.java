package etu.imt.cardgame.DeroulementDeJeu;

import etu.imt.cardgame.Champion;
import etu.imt.cardgame.Monsters.HealerMonster;
import etu.imt.cardgame.Monsters.Monster;
import etu.imt.cardgame.Monsters.MonsterBuilder;
import etu.imt.cardgame.Monsters.SupportMonster;

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
    public List<Monster> cemetery = new ArrayList<>();
    private static Logger log;
    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<Monster> attackUsed = new ArrayList<>();

    public PlateauDeJeu(Champion joueur1, Champion joueur2) throws IOException {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        log=Logger.getLogger("cardGame");
        InputStream fis =  new FileInputStream("src/main/java/resources/log.properties");
        LogManager.getLogManager().readConfiguration(fis);
    }

    /**
     * Starting point of a game, each player will begin their turn with a random deck of 5 monsters
     * @throws Exception
     */
    public void debuterPartie() throws Exception {
        Monster choice;
        log.info(String.format("La partie débute avec les joueurs: %s contre %s", joueur1, joueur2));
        for (int i = 0; i <= 5; i++) {
            choice = drawRandomMonster();
            log.info(String.format("%s à tiré la carte: %s", joueur1.getName(), choice.getName()));
            joueur1.addMonster(choice);
        }

        for (int i = 0; i <= 5; i++) {
            choice = drawRandomMonster();
            log.info(String.format("%s à tiré la carte: %s", joueur2.getName(), choice.getName()));
            joueur2.addMonster(choice);
        }
        tourJoueur(joueur1, joueur2);
    }

    /**
     * Function to start the turn of a player, in order what happens is:
     * 1- Clear the list of monsters that used an attack during the player turns
     * 2- Draw a random monster using the drawRandomMonster function
     * 3- Log the beginning of the turn and the drawn card
     * 4- Present the menu to the player
     * Once every step of a turn is done, we check if the game ended, otherwise we swap target and player
     * @param joueur the player running the action
     * @param target the player that while most likely be targetted, enemy of joueur
     * @throws Exception
     */
    public void tourJoueur(Champion joueur, Champion target) throws Exception {
        // Tour d'un joueur
        // Réinitialise les units qui ont attacké
        attackUsed.clear();
        Monster choice = drawRandomMonster();
        System.out.println("----------------------------------------");
        logConsoleAndFile(String.format("%s commence son tour et pioche: %s", joueur.getName(), choice.getName()));
        // On présente le menu au joueur et une fois qu'il sort du menu on vérifie que la partie ne soit pas fini
        menuTour(joueur, target);
        if (!finDePartie()) {
            // le joueur actuel devient cible et la cible devient le joueur
            tourJoueur(target, joueur);
        }
        else{
            logConsoleAndFile(String.format("La partie est fini, le vainqueur est %s",
                    target.getHealth()<=0? joueur.getName() : target.getName()
            ));
        }
    }

    /**
     * Main menu of a turn
     * Show the menu until the player chooses to exit
     * Always check if the game ended before showing the menu to avoid negative value
     * @param joueur the player running the action
     * @param target the player that while most likely be targetted, enemy of joueur
     * @throws Exception in case of using Necro(but not the case so kinda useless but w/E)
     */
    public void menuTour(Champion joueur, Champion target) throws Exception {
        boolean usedCard = false,usedAbility = false;
        int choiceMenu;
        do {
            if (finDePartie()){
                logConsoleAndFile(String.format("La partie est fini, le vainqueur est %s",
                        target.getHealth()<=0? joueur.getName() : target.getName()
                ));
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
                    // Dont look at that line, Horrible way to find last card
                    logConsoleAndFile(String.format("La carte %s a été posée%n",
                            joueur.getOnBoard().get(joueur.getOnBoard().size() - 1).getName()));
                    break;
                case 2:
                    // Affiche les cartes du DECK du joueur, à ne pas confondre avec le 3 qui affiche les monstres du plateau
                    System.out.println("Les monstres de votre main");
                    joueur.getDeck().forEach((monster ->
                            System.out.printf(" | ID : %d | Nom : %s | Santé : %d | Attaque : %d%n",
                                    monster.getId(),
                                    monster.getName(),
                                    monster.getHealth(),
                                    monster.getPower()
                            )
                    ));
                    break;
                case 3:
                    // Affiche les cartes du PLATEAU du joueur et de son adversaire, à ne pas confondre avec le 2 qui affiche le deck
                    System.out.println("Voir les monstres sur le plateau");
                    System.out.printf("Les monstres de %s avec: %s PV: %n", joueur.getName(), joueur.getHealth());
                    if (!joueur.getOnBoard().isEmpty())
                        joueur.getOnBoard().forEach((monster ->
                                System.out.printf(" | ID : %d | Nom : %s | Santé : %d | Attaque : %d%n",
                                        monster.getId(),
                                        monster.getName(),
                                        monster.getHealth(),
                                        monster.getPower()
                                )
                        ));
                    System.out.printf("Les monstres de %s avec: %s PV: %n", target.getName(), target.getHealth());
                    if (!target.getOnBoard().isEmpty())
                        target.getOnBoard().forEach((monster ->
                                System.out.printf(" | ID : %d | Nom : %s | Santé : %d | Attaque : %d%n",
                                        monster.getId(),
                                        monster.getName(),
                                        monster.getHealth(),
                                        monster.getPower()
                                )
                        ));
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
                    // Present the allies menu and make him choose
                    if (joueur.getAbility().name()=="Soin"){
                        menuTargetAlly(joueur);
                        int targetInt = scanner.nextInt();
                        if (targetInt==0){
                            logConsoleAndFile(String.format("%s utilise la compétence %s sur lui même",
                                    joueur.getName(),
                                    joueur.getAbility().name())
                            );
                            joueur.useAbility(joueur);
                        }
                        else{
                            logConsoleAndFile(String.format("%s utilise la compétence %s sur %s",
                                    joueur.getName(),
                                    joueur.getAbility().name(),
                                    joueur.getBoardMonsterById(targetInt).getName()
                            ));
                            joueur.useAbility(joueur.getBoardMonsterById(targetInt));
                        }
                    }
                    else{
                        menuTarget(target);
                        int targetInt = scanner.nextInt();
                        if (targetInt==0){
                            logConsoleAndFile(String.format("%s utilise la compétence %s sur %s",
                                    joueur.getName(),
                                    joueur.getAbility().name(),
                                    target.getName()
                            ));
                            joueur.useAbility(target);
                        }
                        else{
                            logConsoleAndFile(String.format("%s utilise la compétence %s sur %s",
                                    joueur.getName(),
                                    joueur.getAbility().name(),
                                    target.getBoardMonsterById(targetInt).getName()
                            ));
                            joueur.useAbility(target.getBoardMonsterById(targetInt));
                        }
                    }
                    break;
                case 6:
                    logConsoleAndFile(String.format("Le tour de %s est fini, début du tour de %s", joueur.getName(), target.getName()));
                    break;
                default:
                    System.out.println("Choix invalide");
                    break;

            }
        }while(choiceMenu!=6);
    }

    /**
     * boolean operator to check if either champion is dead
     * @return boolean
     */
    public boolean finDePartie() {
        return joueur1.getHealth() <= 0 || joueur2.getHealth() <= 0 ;
    }

    /**
     * Draw a random monster using Random lib
     * @return Monster
     */
    public Monster drawRandomMonster(){
        Random random = new Random();
        int choice = random.nextInt(5);
        return switch (choice) {
            case 0 -> MonsterBuilder.buildMonster("Healer");
            // case 1 -> MonsterBuilder.buildMonster("Necro"); Necro not yet implemented, removed the ability to draw it
            case 2 -> MonsterBuilder.buildMonster("Shield");
            case 3 -> MonsterBuilder.buildMonster("Kamikaz");
            case 4 -> MonsterBuilder.buildMonster("Support");
            default -> MonsterBuilder.buildMonster("Simple");
        };
    }

    /**
     * To simplify the code in menuTour, we separate the attack menu from the overall menu,
     * For more readability
     * @param attaquant
     * @param target
     * @throws Exception
     */
    public void attackTurn(Champion attaquant, Champion target) throws Exception {
        ArrayList<Integer> idAvailable = new ArrayList<>();
        System.out.println("Attaquer");
        System.out.println("Voici vos monstres disponible à l'attaque:");
        attaquant.getOnBoard().forEach((monster -> {
            if (!attackUsed.contains(monster)) {
                System.out.printf("ID: %s,Nom: %s, PV: %s, CP: %s %n",
                        monster.getId(),
                        monster.getName(),
                        monster.getHealth(),
                        monster.getPower()
                );
                idAvailable.add(monster.getId());
            }
            }));
        int choice;
        do{
            System.out.println("Avec quel monstre voulez-vous attaquer? %nPour sortir du menu attaquer, entrer 999.");
            choice = scanner.nextInt();
            if (choice==999) break;
            if (!idAvailable.contains(choice)){
                System.out.println("Id non valide!");
                break;
            }
            idAvailable.remove((Integer)choice);
            attackUsed.add(attaquant.getBoardMonsterById(choice));
            // Si le monstre est un monstre soigneur ou support, on montre un menu allié
            if(attaquant.getBoardMonsterById(choice).getClass() == SupportMonster.class
                    || attaquant.getBoardMonsterById(choice).getClass()== HealerMonster.class)
            {
                menuTargetAlly(attaquant);
                int targetInt = scanner.nextInt();
                if (targetInt==0){
                    String msg = String.format("Le monstre %s utilise son pouvoir sur le champion %s. Il lui reste maintenant %s PVs",
                            attaquant.getBoardMonsterById(choice).getName(),
                            attaquant.getName()
                    );
                    logConsoleAndFile(msg);
                    attaquant.getBoardMonsterById(choice).targetUnit(attaquant);
                }
                else{
                    String msg = String.format("Le monstre %s utilise son pouvoir sur le monstre %s. Il lui reste maintenant %s PVs",
                            attaquant.getBoardMonsterById(choice).getName(),
                            attaquant.getName()
                    );
                    logConsoleAndFile(msg);
                    attaquant.getBoardMonsterById(choice).targetUnit(attaquant.getBoardMonsterById(choice));
                }
                break;
            }
            menuTarget(target);
            int targetInt = scanner.nextInt();
            if (targetInt==0){
                // Before attacking the champion, we check if he has a shield
                Monster isShielded = target.getShield();
                // if a value is found using getShield, we target the shield instead
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
                // else we can attack the champion
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

    /**
     * Used to verify if any players has a dead monster on its field,
     * we then add all dead monsters and add it to the cemetery
     * @param c1
     * @param c2
     */
    public void checkDeadMonster(Champion c1, Champion c2) {
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

    /**
     * Helper function to print a full menu, because we print them a lot and having a way to quickly call it without making the code a mess is cleaner
     * @param target
     */
    public static void menuTarget(Champion target){
        System.out.println("Voici le plateau de votre adversaire:");
        System.out.printf("Champion: Nom: %s, PV: %s%n", target.getName(), target.getHealth());
        System.out.println("Monstres:");
        target.getOnBoard().forEach((monster ->
                System.out.printf("ID: %s,Nom: %s, PV: %s, CP: %s %n",
                        monster.getId(),
                        monster.getName(),
                        monster.getHealth(),
                        monster.getPower()
                )
        ));
        System.out.println("Quel monstre voulez-vous attaquer? Pour sélectionner le Champion adverse, entrer 0");
    }

    /**
     * Helper function to print a full menu, because we print them a lot and having a way to quickly call it without making the code a mess is cleaner
     * @param target
     */
    public static void menuTargetAlly(Champion target){
        System.out.println("Voici votre plateau:");
        System.out.printf("Champion: Nom: %s, PV: %s%n", target.getName(), target.getHealth());
        System.out.println("Monstres:");
        target.getOnBoard().forEach((monster ->
                System.out.printf("ID: %s,Nom: %s, PV: %s, CP: %s %n",
                        monster.getId(),
                        monster.getName(),
                        monster.getHealth(),
                        monster.getPower()
                )
        ));
        System.out.println("Quel monstre voulez-vous cibler? Pour sélectionner votre champion, entrer 0");
    }

    /**
     * Helper function to print both to console and log file at the same time,
     * createe to remove redundant and not readable code
     *
     * @param msg
     */
    public static void logConsoleAndFile(String msg){
        System.out.println(msg);
        log.info(msg);
    }
}
