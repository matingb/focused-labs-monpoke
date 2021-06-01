package com.monpoke;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
}