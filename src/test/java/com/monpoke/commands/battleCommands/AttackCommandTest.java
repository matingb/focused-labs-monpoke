package com.monpoke.commands.battleCommands;

import com.monpoke.Monpoke;
import com.monpoke.Team;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttackCommandTest {

    AttackCommand attackCommand;
    Team currentTeam ;
    Team opposingTeam;

    @Before
    public void setup() {
        attackCommand = new AttackCommand(new String[]{});
        currentTeam = mock(Team.class);
        opposingTeam = mock(Team.class);
    }
    @Test
    public void whenThereIsNoChosenMonpokeForFirstTeamWhenExecuteShouldGetAnError() {
        when(currentTeam.getChosenMonpoke()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            attackCommand.execute(currentTeam, opposingTeam);
        });

        assertEquals("Rule violation - both teams did not have chosen monpoke out", exception.getMessage());
    }

    @Test
    public void whenThereIsNoChosenMonpokeForSecondTeamWhenExecuteShouldGetAnError() {
        when(currentTeam.getChosenMonpoke()).thenReturn(new Monpoke("monpokeName",5,5));
        when(opposingTeam.getChosenMonpoke()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            attackCommand.execute(currentTeam, opposingTeam);
        });

        assertEquals("Rule violation - both teams did not have chosen monpoke out", exception.getMessage());
    }

    @Test
    public void cannotCreateAttackCommandWithMoreThan1Parameter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new AttackCommand(new String[]{"30"});
        });

        assertEquals("Expecting 0 parameters for command but were received 1", exception.getMessage());
    }
}
