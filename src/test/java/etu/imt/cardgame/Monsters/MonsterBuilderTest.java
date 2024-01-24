package etu.imt.cardgame.Monsters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterBuilderTest {
    @Test
    void buildMonster() {
        Monster simple = MonsterBuilder.buildMonster("Simple");
        Monster shield = MonsterBuilder.buildMonster("Shield");
        Monster support = MonsterBuilder.buildMonster("Support");
        Monster healer = MonsterBuilder.buildMonster("Healer");
        Monster necro = MonsterBuilder.buildMonster("Necro");
        assertEquals("Simplet", simple.getName());
        assertEquals("Protecteur", shield.getName());
        assertEquals("Mascotte", support.getName());
        assertEquals("Soigneur", healer.getName());
        assertEquals("Necromancien", necro.getName());
    }
}