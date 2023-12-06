package com.monpoke.commands;

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

    String[] commandParameters = new String[]{"ATTACK"};

    @Before
    public void setup() {
        attackCommand = new AttackCommand();
        currentTeam = mock(Team.class);
        opposingTeam = mock(Team.class);
    }
    @Test
    public void whenThereIsNoChosenMonpokeForFirstTeamWhenExecuteShouldGetAnError() {
        when(currentTeam.getChosenMonpoke()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            attackCommand.execute(commandParameters, currentTeam, opposingTeam);
        });

        assertEquals("Rule violation - both teams did not have chosen monpoke out", exception.getMessage());
    }

    @Test
    public void whenThereIsNoChosenMonpokeForSecondTeamWhenExecuteShouldGetAnError() {
        when(currentTeam.getChosenMonpoke()).thenReturn(new Monpoke("monpokeName",5,5));
        when(opposingTeam.getChosenMonpoke()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            attackCommand.execute(commandParameters, currentTeam, opposingTeam);
        });

        assertEquals("Rule violation - both teams did not have chosen monpoke out", exception.getMessage());
    }
}
