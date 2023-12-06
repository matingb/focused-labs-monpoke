package com.monpoke;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TeamTest {
    Team team;
    String firstMockName;
    Monpoke firstMockPoke;
    String secMockName;
    Monpoke secMockPoke;

    @Before
    public void setUp() {
        firstMockPoke = mock(Monpoke.class);
        secMockPoke = mock(Monpoke.class);
        firstMockName = "MockPoke1";
        secMockName = "MockPoke2";
        when(firstMockPoke.getName()).thenReturn(firstMockName);
        when(secMockPoke.getName()).thenReturn(secMockName);

        team = new Team("TestName");
        team.addMonpoke(firstMockPoke);
        team.addMonpoke(secMockPoke);
    }

    @Test
    public void getTeamName() {
        assertEquals("TestName", team.getTeamName());
    }

    @Test
    public void chosenMonpoke() {
        when(secMockPoke.getCurrentHealth()).thenReturn(1);

        team.chooseMonpoke(secMockName);

        assertEquals(secMockPoke, team.getChosenMonpoke());
    }

    @Test
    public void invalidChooseMonpoke() {
        try {
            team.chooseMonpoke("Dudegeo");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Rule violation - Team TestName does not own the chosen Monpoke Dudegeo", e.getMessage());
        }
    }

    @Test
    public void chooseAFaintedMonSAhouldThrowAnError() {
        when(firstMockPoke.getCurrentHealth()).thenReturn(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            team.chooseMonpoke(firstMockName);
        });

        assertEquals("Rule violation - cannot choose a fainted monpoke", exception.getMessage());
    }

    @Test
    public void checkMonpokeStatus() {
        when(firstMockPoke.getCurrentHealth()).thenReturn(1);
        team.chooseMonpoke(firstMockName);
        when(firstMockPoke.getCurrentHealth()).thenReturn(0);
        when(secMockPoke.getCurrentHealth()).thenReturn(1);

        boolean didFaint = team.didMonpokeFaint();
        assertTrue(didFaint);
        assertEquals(1, team.getNumAliveMonpoke());

        team.chooseMonpoke(secMockName);
        when(secMockPoke.getCurrentHealth()).thenReturn(1);
        didFaint = team.didMonpokeFaint();
        assertFalse(didFaint);
        assertEquals(secMockPoke, team.getChosenMonpoke());
        assertEquals(1, team.getNumAliveMonpoke());
    }

    @Test
    public void givenATeamWithMonpokesWhenTriesToHealAMonThatIsNotInTheTeamThenGetsAnError() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            team.healMonpoke("nonPresentMonpoke", 30);
        });

        assertEquals("Rule violation - cannot heal a monpoke that is not in the team", exception.getMessage());
    }

    @Test
    public void healAFaintedMonSAhouldThrowAnError() {
        when(firstMockPoke.getCurrentHealth()).thenReturn(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            team.healMonpoke(firstMockName, 10);
        });

        assertEquals("Rule violation - cannot heal a fainted monpoke", exception.getMessage());
    }

    @Test
    public void whenHealsAMonPokeShouldGetTheAmountHealed() {
        when(firstMockPoke.getCurrentHealth()).thenReturn(1);
        when(firstMockPoke.receiveHeal(10)).thenReturn(6);

        int amountHealed = team.healMonpoke(firstMockName, 10);

        assertEquals(6, amountHealed);
    }

    @Test
    public void givenATeamWithMonpokesWhenTriesToReviveAMonThatIsNotInTheTeamThenGetsAnError() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            team.reviveMonpoke("nonPresentMonpoke");
        });

        assertEquals("Rule violation - cannot revive a monpoke that is not in the team", exception.getMessage());
    }

    @Test
    public void reviveMoreThanOnceGetsAnError() {
        team.reviveMonpoke(firstMockName);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            team.reviveMonpoke(firstMockName);
        });

        assertEquals("Rule violation - each team only gets 1 revive per match", exception.getMessage());
    }

    @Test
    public void givenAMonWithHealthWhenTriesToReviveShouldGetAnError() {
        when(firstMockPoke.getCurrentHealth()).thenReturn(1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            team.reviveMonpoke(firstMockName);
        });

        assertEquals("Rule violation - cannot revive an alive monpoke", exception.getMessage());
    }

    @Test
    public void reviveShouldHealFullHPOfMonpoke() {
        when(firstMockPoke.getMaxHealth()).thenReturn(20);
        when(firstMockPoke.getCurrentHealth()).thenReturn(0);

        team.reviveMonpoke(firstMockName);

        verify(firstMockPoke).receiveHeal(20);
    }
}
