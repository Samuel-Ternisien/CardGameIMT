package etu.imt.cardgame.Monsters;

import etu.imt.cardgame.Abilities;
import etu.imt.cardgame.Champion;
import etu.imt.cardgame.DeroulementDeJeu.PlateauDeJeu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlateauDeJeuTest {
    @Test
    void testAttackBetweenTwoMonsters() throws Exception {
        Champion c1 = new Champion("Champion1", 50, Abilities.Attaque);
        Champion c2 = new Champion("Champion2", 50, Abilities.Attaque);
        PlateauDeJeu pdj = new PlateauDeJeu(c1, c2);
        // Ajoute une carte au deck de chaque joueur puis la pose sur le plateau
        c1.addMonster(new SimpleMonster("Simplet", 50, 25));
        c2.addMonster(new SimpleMonster("Simplet", 50, 25));
        c1.jouerCarte(1);
        c2.jouerCarte(2);
        // Le monstre de champion 1 attaque 2 fois le monstre de champion 2 (2*25=50 donc le monstre meurt)
        c1.getBoardMonsterById(1).targetUnit(c2.getBoardMonsterById(2));
        c1.getBoardMonsterById(1).targetUnit(c2.getBoardMonsterById(2));
        // Check les monstres morts dans le plateau
        pdj.checkDeadMonster(c1, c2);
        // On vérifie que le monstre du cimetière à bien le même nom que celui mort
        assertEquals(pdj.cemetery.get(0).getName(),"Simplet_2");
        // On vérifie que son monstre n'est pu dans son plateau de jeu
        assertTrue(c2.getOnBoard().isEmpty());
    }
    @Test
    void testCombatBetweenTwoChampions() throws Exception{
        Champion c1 = new Champion("Champion1", 50, Abilities.Attaque);
        Champion c2 = new Champion("Champion2", 50, Abilities.Attaque);
        PlateauDeJeu pdj = new PlateauDeJeu(c1, c2);
        // Ajoute une carte au deck de chaque joueur puis la pose sur le plateau
        c1.addMonster(new SimpleMonster("Simplet", 50, 25));
        c2.addMonster(new SimpleMonster("Simplet", 50, 25));
        c1.jouerCarte(3);
        c2.jouerCarte(4);
        // Le monstre de champion 1 attaque 2 fois le champion 2 (2*25=50 donc le champion meurt)
        c1.getBoardMonsterById(3).targetUnit(c2);
        c1.getBoardMonsterById(3).targetUnit(c2);

        // On a juste à vérifier grâce à la fonction finDePartie que la partie est finie
        assertTrue(pdj.finDePartie());
    }

    @Test
    void testAbilities() throws Exception{
        Champion c1 = new Champion("Champion1", 50, Abilities.Soin);
        Champion c2 = new Champion("Champion2", 50, Abilities.Attaque);
        // On vérifie que la compétence attaque fonctionne
        c2.useAbility(c1);
        assertEquals(c1.getHealth(),40);
        // On vérifie que la compétence soin fonctionne
        c1.useAbility(c1);
        assertEquals(c1.getHealth(),60);
    }
}
