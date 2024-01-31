package etu.imt.cardgame.Monsters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonsterBuilderTest {
    @Test
    void buildMonster() {
        Monster simple = MonsterBuilder.buildMonster("Simple");
        Monster shield = MonsterBuilder.buildMonster("Shield");
        Monster support = MonsterBuilder.buildMonster("Support");
        Monster healer = MonsterBuilder.buildMonster("Healer");
        Monster necro = MonsterBuilder.buildMonster("Necro");
        assertEquals("Simplet_1", simple.getName());
        assertEquals("Protecteur_2", shield.getName());
        assertEquals("Mascotte_3", support.getName());
        assertEquals("Soigneur_4", healer.getName());
        assertEquals("Necromancien_5", necro.getName());
    }
}