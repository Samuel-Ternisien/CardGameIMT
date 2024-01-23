package etu.imt.cardgame.Monsters;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NecroMonsterTest {

    @org.junit.jupiter.api.Test
    void resurrectAlive() {
        Exception exception = assertThrows(Exception.class, () -> {
            Monster m1 = new SimpleMonster("m1", 50, 10);
            NecroMonster m2 = new NecroMonster("m2", 50, 10);
            m1 = m2.resurrect(m1);
        });
        String expectedMessage = "Target is not dead, you can't corrupt the living";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @org.junit.jupiter.api.Test
    void resurrectAlreadyDead() {
        Exception exception = assertThrows(Exception.class, () -> {
            Monster m1 = new SimpleMonster("Zombified_m1", 0, 10);
            NecroMonster m2 = new NecroMonster("m2", 50, 10);
            m1 = m2.resurrect(m1);
        });
        String expectedMessage = "Target is already dead, let some rest to the dead";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @org.junit.jupiter.api.Test
    void ressurect() {
        try {
            Monster m1 = new SimpleMonster("m1", 0, 10);
            int old_power = m1.getPower();
            NecroMonster m2 = new NecroMonster("m2", 50, 10);
            m1 = m2.resurrect(m1);
            assertTrue(
                    m1.getHealth()==7 &&
                    m1.getPower()==old_power*0.80 &&
                    m1.getName().startsWith("Zombified_"
                    ));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}