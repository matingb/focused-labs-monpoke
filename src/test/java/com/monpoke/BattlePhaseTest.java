package com.monpoke;

import com.monpoke.commands.battleCommands.AttackCommand;
import com.monpoke.commands.battleCommands.ChooseCommand;
import com.monpoke.commands.battleCommands.HealCommand;
import com.monpoke.commands.battleCommands.ReviveCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BattlePhaseTest {

    BattlePhase battlePhase;
    Team mockFirstTeam;
    Monpoke mockMon1;
    Team mockSecTeam;
    Monpoke mockMon2;

    ChooseCommand chooseCommand = new ChooseCommand(new String[]{"TestMon1"});
    AttackCommand attackCommand = new AttackCommand(new String[]{});
    HealCommand healCommand = new HealCommand(new String[]{"30"});
    ReviveCommand reviveCommand = new ReviveCommand(new String[]{"TestMon1"});

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

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            battlePhase.battle(chooseCommand);
        });

        assertEquals("Rule violation - battle has not been started", exception.getMessage());
    }

    @Test
    public void healEndsTheTurn() {
        battlePhase.battle(healCommand);

        assertEquals(1, battlePhase.getTurnCounter());
    }

    @Test
    public void reviveEndsTheTurn() {
        battlePhase.battle(reviveCommand);

        assertEquals(1, battlePhase.getTurnCounter());
    }

    @Test
    public void successfulChooseMon() {
        String output = battlePhase.battle(chooseCommand);
        assertEquals("TestMon1 has entered the battle!", output);
    }

    @Test
    public void successfulFightMon() {
        String output = battlePhase.battle(attackCommand);

        verify(mockMon2).receiveAttack(mockMon1);
        assertEquals("TestMon1 attacked TestMon2 for 1 damage!", output);
    }

    @Test
    public void successfulHealMon() {
        when(mockFirstTeam.healMonpoke(mockMon1.getName(), 30)).thenReturn(5);
        when(mockMon1.getCurrentHealth()).thenReturn(10);

        String output = battlePhase.battle(healCommand);

        verify(mockFirstTeam).healMonpoke(mockMon1.getName(), 30);
        assertEquals("TestMon1 healed for " + 5 + " to " + 10, output);
    }

    @Test
    public void successfulReviveMon() {
        String output = battlePhase.battle(reviveCommand);

        verify(mockFirstTeam).reviveMonpoke("TestMon1");
        assertEquals("TestMon1 has been revived", output);
    }

    @Test
    public void successfulReviveDiffentMonThanSelected() {
        when(mockFirstTeam.getChosenMonpoke()).thenReturn(null);

        String output = battlePhase.battle(reviveCommand);

        verify(mockFirstTeam).reviveMonpoke("TestMon1");
        assertEquals("TestMon1 has been revived\n" + "TestMon1 has entered the battle!", output);
    }

    @Test
    public void successfulDefeatMon() {
        when(mockSecTeam.didMonpokeFaint()).thenReturn(true);
        when(mockSecTeam.getNumAliveMonpoke()).thenReturn(1);

        String output = battlePhase.battle(attackCommand);
        String expectedOutput = "TestMon1 attacked TestMon2 for 1 damage!";
        expectedOutput += "\nTestMon2 has been defeated!";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void getWinner() {
        when(mockSecTeam.didMonpokeFaint()).thenReturn(true);
        when(mockSecTeam.getNumAliveMonpoke()).thenReturn(0);

        String output = battlePhase.battle(attackCommand);
        String expectedOutput = "TestMon1 attacked TestMon2 for 1 damage!";
        expectedOutput += "\nTestMon2 has been defeated!";
        assertEquals(expectedOutput, output);
        assertEquals(mockFirstTeam, battlePhase.getWinner());
    }
}