package com.monpoke;

import org.junit.Test;

import static org.junit.Assert.*;

public class MonpokeTest {

    @Test
    public void violateMinimums() {
        try {
            new Monpoke("TestMon", 0, 1);
            fail();
        } catch (Exception e) {
            assertEquals("Rule violated - health or attack is less than 1", e.getMessage());
        }

        try {
            new Monpoke("TestMon", 1, 0);
            fail();
        } catch (Exception e) {
            assertEquals("Rule violated - health or attack is less than 1", e.getMessage());
        }
    }

    @Test
    public void takeDamage() {
        Monpoke firstMon = new Monpoke("FirstMon", 5, 1);
        Monpoke secMon = new Monpoke("SecondMon", 2, 5);

        Monpoke attackingMon = new Monpoke("AttackingMon", 4, 4);

        int firstHP = firstMon.receiveAttack(attackingMon);
        int secHP = secMon.receiveAttack(attackingMon);

        assertEquals(1, firstHP);
        assertEquals(-2, secHP);
    }


    @Test
    public void nameGetter() {
        Monpoke mon = new Monpoke("NameTester", 3, 1);
        assertEquals("NameTester", mon.getName());
    }

    @Test
    public void attackGetter() {
        Monpoke mon = new Monpoke("AttackTester", 3, 11);
        assertEquals(11, mon.getAttack());
    }

    @Test
    public void healthGetter() {
        Monpoke mon = new Monpoke("HealthTester", 3, 11);
        assertEquals(3, mon.getCurrentHealth());
    }

    @Test
    public void healShouldRestoreMonpokeHealth() {
        Monpoke mon = givenAMonWith(5, 2);

        int amountHealed = mon.heal(2);

        assertEquals(4, mon.getCurrentHealth());
        assertEquals(2, amountHealed);
    }

    @Test
    public void monpokeHealthAfterHealShouldNotBeBiggerThanInitialHealth() {
        Monpoke mon = givenAMonWith(6, 2);

        int amountHealed = mon.heal(10);

        assertEquals(6, mon.getCurrentHealth());
        assertEquals(4, amountHealed);
    }

    @Test
    public void healAFaintMonSAhouldThrowAnError() {
        Monpoke mon = givenAMonWith(6, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mon.heal(10);
        });

        assertEquals("Rule violation - cannot heal a monpoke with a current HP of less than 1", exception.getMessage());
    }

    private static Monpoke givenAMonWith(int maxHealth, int actualHealth) {
        Monpoke mon = new Monpoke("HealthTester", maxHealth, 11);
        Monpoke attackingMonpoke = new Monpoke("HealthTester", 5, maxHealth - actualHealth);
        mon.receiveAttack(attackingMonpoke);
        return mon;
    }
}