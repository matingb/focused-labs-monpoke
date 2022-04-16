package com.monpoke;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhaseHandlerTest {

    PhaseHandler phaseHandler;
    Scanner mockScanner;
    CreatePhase mockCreate;
    BattlePhase mockBattle;
    Team mockWinner;

    String testCreate = "CREATE Rocket Meekachu 3 1";
    String[] testCreateArr = new String[]{"CREATE", "Rocket", "Meekachu", "3", "1"};
    String testChoose = "ICHOOSEYOU Meekachu";
    String[] testChooseArr = new String[]{"ICHOOSEYOU", "Meekachu"};
    String testAttack = "ATTACK";
    String[] testAttackArr = new String[]{"ATTACK"};
    String invalidComm = "DO A THING";

    @Before
    public void setUp() {
        mockScanner = mock(Scanner.class);
        mockCreate = mock(CreatePhase.class);
        when(mockCreate.create(testCreateArr)).thenReturn("Test Create Output");

        mockBattle = mock(BattlePhase.class);
        when(mockBattle.battle(testChooseArr)).thenReturn("Test Choose Output");
        when(mockBattle.battle(testAttackArr)).thenReturn("Test Attack Output");

        mockWinner = mock(Team.class);
        when(mockWinner.getTeamName()).thenReturn("Winning Test Team");

        phaseHandler = new PhaseHandler();
    }

    @Test
    @Ignore
    public void fullGameOutput() {
        when(mockScanner.nextLine()).thenReturn(testCreate).thenReturn(testChoose).thenReturn(testAttack);
        when(mockCreate.readyToBattle()).thenReturn(true);
        when(mockBattle.getWinner()).thenReturn(null).thenReturn(null).thenReturn(null).thenReturn(mockWinner);

        ArrayList<String> expectedOutput = new ArrayList<String>(Arrays.asList("Test Create Output", "Test Choose Output",
                "Test Attack Output", "Winning Test Team is the winner!"));
        ArrayList<String> output = phaseHandler.runGame(mockScanner, mockCreate, mockBattle);
        assertEquals(expectedOutput, output);
    }

    @Test
    public void unexpectedEndOfInputFile() {
        when(mockScanner.nextLine()).thenThrow(NoSuchElementException.class);
        try {
            phaseHandler.runGame(mockScanner, mockCreate, mockBattle);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Input file has reached its end without a winner", e.getMessage());
        }
    }

    @Test
    public void createDuringBattle() {
        when(mockScanner.nextLine()).thenReturn(testChoose).thenReturn(testCreate);
        when(mockCreate.readyToBattle()).thenReturn(true);
        try {
            phaseHandler.runGame(mockScanner, mockCreate, mockBattle);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Rule violated - battle is already in progress", e.getMessage());
        }
    }

    @Test
    public void prematureBattleStart() {
        when(mockScanner.nextLine()).thenReturn(testChoose);
        try {
            phaseHandler.runGame(mockScanner, mockCreate, mockBattle);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Rule violated - teams not created", e.getMessage());
        }
    }

    @Test
    public void invalidInput() {
        when(mockScanner.nextLine()).thenReturn(invalidComm);
        try {
            phaseHandler.runGame(mockScanner, mockCreate, mockBattle);
        } catch (IllegalArgumentException e) {
            assertEquals("Unreadable command input", e.getMessage());
        }
    }

}
