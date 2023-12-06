package com.monpoke;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattlePhaseTest {

    BattlePhase battlePhase;
    Team mockFirstTeam;
    Monpoke mockMon1;
    Team mockSecTeam;
    Monpoke mockMon2;

    String[] chooseString = new String[]{"ICHOOSEYOU", "TestMon1"};
    String[] attackString = new String[]{"ATTACK"};
    String[] healString = new String[]{"HEAL", "30"};
    String[] reviveString = new String[]{"REVIVE", "TestMon1"};

    @Before
    public void setUp() {
        mockFirstTeam = mock(Team.class);
        mockMon1 = mock(Monpoke.class);
        mockSecTeam = mock(Team.class);
        mockMon2 = mock(Monpoke.class);

        Team[] mockTeams = new Team[]{mockFirstTeam, mockSecTeam};
        when(mockFirstTeam.getChosenMonpoke()).thenReturn(mockMon1);
        when(mockFirstTeam.didMonpokeFaint()).thenReturn(false);
        when(mockSecTeam.getChosenMonpoke()).thenReturn(mockMon2);
        when(mockSecTeam.didMonpokeFaint()).thenReturn(false);

        when(mockMon1.getAttack()).thenReturn(1);
        when(mockMon1.getName()).thenReturn("TestMon1");
        when(mockMon2.getAttack()).thenReturn(2);
        when(mockMon2.getName()).thenReturn("TestMon2");

        battlePhase = new BattlePhase();
        battlePhase.startBattle(mockTeams);
    }

    @Test
    public void battleNotStarted() {
        battlePhase = new BattlePhase();
        validateExceptionDuringBattleForCommand(chooseString, "Rule violation - battle has not been started");
    }

    @Test
    public void noChosenMonpokeForAttack() {
        when(mockFirstTeam.getChosenMonpoke()).thenReturn(null);
        validateExceptionDuringBattleForCommand(attackString, "Rule violation - both teams did not have chosen monpoke out");
    }

    @Test
    public void healCommandNoChosenMonpokeForFirstTeam() {
        when(mockFirstTeam.getChosenMonpoke()).thenReturn(null);
        validateExceptionDuringBattleForCommand(healString, "Rule violation - both teams did not have chosen monpoke out");
    }

    @Test
    public void healCommandNoChosenMonpokeForSecondTeam() {
        when(mockFirstTeam.getChosenMonpoke()).thenReturn(mockMon1);
        when(mockSecTeam.getChosenMonpoke()).thenReturn(null);

        validateExceptionDuringBattleForCommand(healString, "Rule violation - both teams did not have chosen monpoke out");
    }

    @Test
    public void healEndsTheTurn() {
        battlePhase.battle(healString);

        assertEquals(1, battlePhase.getTurnCounter());
    }

    @Test
    public void reviveEndsTheTurn() {
        battlePhase.battle(reviveString);

        assertEquals(1, battlePhase.getTurnCounter());
    }

    @Test
    public void successfulChooseMon() {
        String output = battlePhase.battle(chooseString);
        assertEquals("TestMon1 has entered the battle!", output);
    }

    @Test
    public void successfulFightMon() {
        String output = battlePhase.battle(attackString);
        assertEquals("TestMon1 attacked TestMon2 for 1 damage!", output);
    }

    @Test
    public void successfulHealMon() {
        when(mockFirstTeam.healMonpoke(mockMon1.getName(), 30)).thenReturn(5);
        when(mockMon1.getCurrentHealth()).thenReturn(10);

        String output = battlePhase.battle(healString);

        assertEquals("TestMon1 healed for " + 5 + " to " + 10, output);
    }

    @Test
    public void successfulDefeatMon() {
        when(mockSecTeam.didMonpokeFaint()).thenReturn(true);
        when(mockSecTeam.getNumMonpoke()).thenReturn(1);

        String output = battlePhase.battle(attackString);
        String expectedOutput = "TestMon1 attacked TestMon2 for 1 damage!";
        expectedOutput += "\nTestMon2 has been defeated!";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void getWinner() {
        when(mockSecTeam.didMonpokeFaint()).thenReturn(true);
        when(mockSecTeam.getNumMonpoke()).thenReturn(0);

        String output = battlePhase.battle(attackString);
        String expectedOutput = "TestMon1 attacked TestMon2 for 1 damage!";
        expectedOutput += "\nTestMon2 has been defeated!";
        assertEquals(expectedOutput, output);
        assertEquals(mockFirstTeam, battlePhase.getWinner());
    }

    private void validateExceptionDuringBattleForCommand(String[] command, String exceptionMessage) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            battlePhase.battle(command);
        });

        assertEquals(exceptionMessage, exception.getMessage());
    }
}