package com.monpoke.commands;

import com.monpoke.Monpoke;
import com.monpoke.Team;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealCommandTest {

    HealCommand healCommand;
    Team currentTeam ;
    Team opposingTeam;

    @Before
    public void setup() {
        healCommand = new HealCommand();
        currentTeam = mock(Team.class);
        opposingTeam = mock(Team.class);
    }

    @Test
    public void whenThereIsNoChosenMonpokeForFirstTeamWhenExecuteShouldGetAnError() {
        String[] commandParameters = new String[]{"HEAL", "30"};
        when(currentTeam.getChosenMonpoke()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            healCommand.execute(commandParameters, currentTeam, opposingTeam);
        });

        assertEquals("Rule violation - both teams did not have chosen monpoke out", exception.getMessage());
    }

    @Test
    public void whenThereIsNoChosenMonpokeForSecondTeamWhenExecuteShouldGetAnError() {
        String[] commandParameters = new String[]{"HEAL", "30"};
        when(currentTeam.getChosenMonpoke()).thenReturn(new Monpoke("monpokeName",5,5));
        when(opposingTeam.getChosenMonpoke()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            healCommand.execute(commandParameters, currentTeam, opposingTeam);
        });

        assertEquals("Rule violation - both teams did not have chosen monpoke out", exception.getMessage());
    }
}
