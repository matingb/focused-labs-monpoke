package com.monpoke;

import com.monpoke.commands.*;
import com.monpoke.commands.battleCommands.AttackCommand;
import com.monpoke.commands.battleCommands.ChooseCommand;
import com.monpoke.commands.battleCommands.HealCommand;
import com.monpoke.commands.battleCommands.ReviveCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PhaseHandlerTest {

    PhaseHandler phaseHandler;
    Scanner mockScanner;
    CreatePhase mockCreate;
    BattlePhase mockBattle;
    Logger mockLogger;
    Team mockWinner;

    String testCreate = "CREATE Rocket Meekachu 3 1";
    String testChoose = "ICHOOSEYOU Meekachu";
    String testAttack = "ATTACK";
    String testHeal = "HEAL 30";
    String testRevive = "REVIVE Meekachu";
    String invalidComm = "DO A THING";

    @Before
    public void setUp() {
        mockScanner = mock(Scanner.class);
        mockCreate = mock(CreatePhase.class);
        mockLogger = mock(Logger.class);
        when(mockCreate.create(any(CreateCommand.class))).thenReturn("Test Create Output");

        mockBattle = mock(BattlePhase.class);
        when(mockBattle.battle(any(ChooseCommand.class))).thenReturn("Test Choose Output");
        when(mockBattle.battle(any(AttackCommand.class))).thenReturn("Test Attack Output");
        when(mockBattle.battle(any(HealCommand.class))).thenReturn("Test Heal Output");
        when(mockBattle.battle(any(ReviveCommand.class))).thenReturn("Test Revive Output");

        mockWinner = mock(Team.class);
        when(mockWinner.getTeamName()).thenReturn("Winning Test Team");

        phaseHandler = new PhaseHandler();
    }

    @Test
    public void fullGameOutput() {
        when(mockScanner.nextLine()).thenReturn(testCreate).thenReturn(testChoose)
                .thenReturn(testAttack).thenReturn(testHeal).thenReturn(testRevive);
        when(mockCreate.readyToBattle()).thenReturn(true);

        when(mockBattle.getWinner()).thenReturn(null).thenReturn(null).thenReturn(null)
                .thenReturn(null).thenReturn(null).thenReturn(null)
                .thenReturn(null).thenReturn(mockWinner);

        phaseHandler.runGame(mockScanner, mockCreate, mockBattle, mockLogger);

        InOrder order = inOrder(mockLogger);
        order.verify(mockLogger).info("Test Create Output");
        order.verify(mockLogger).info("Test Choose Output");
        order.verify(mockLogger).info("Test Attack Output");
        order.verify(mockLogger).info("Test Heal Output");
        order.verify(mockLogger).info("Test Revive Output");
        order.verify(mockLogger).info("Winning Test Team is the winner!");
    }

    @Test
    public void unexpectedEndOfInputFile() {
        when(mockScanner.nextLine()).thenThrow(NoSuchElementException.class);
        try {
            phaseHandler.runGame(mockScanner, mockCreate, mockBattle, mockLogger);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Input file has reached its end without a winner", e.getMessage());
        }
    }
    @Test
    public void invalidInput() {
        when(mockScanner.nextLine()).thenReturn(invalidComm);
        try {
            phaseHandler.runGame(mockScanner, mockCreate, mockBattle, mockLogger);
        } catch (IllegalArgumentException e) {
            assertEquals("The first commands must be CREATE commands", e.getMessage());
        }
    }

    @Test
    public void invalidBattleInput() {
        when(mockScanner.nextLine()).thenReturn(testCreate).thenReturn(invalidComm);
        try {
            phaseHandler.runGame(mockScanner, mockCreate, mockBattle, mockLogger);
        } catch (IllegalArgumentException e) {
            assertEquals("Unreadable battle command input", e.getMessage());
        }
    }

}
