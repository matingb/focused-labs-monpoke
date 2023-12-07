package com.monpoke.commands.battleCommands;

import com.monpoke.Monpoke;
import com.monpoke.Team;
import com.monpoke.commands.battleCommands.HealCommand;
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
        healCommand = new HealCommand(new String[]{"30"});
        currentTeam = mock(Team.class);
        opposingTeam = mock(Team.class);
    }

    @Test
    public void whenThereIsNoChosenMonpokeForFirstTeamWhenExecuteShouldGetAnError() {
        when(currentTeam.getChosenMonpoke()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            healCommand.execute(currentTeam, opposingTeam);
        });

        assertEquals("Rule violation - both teams did not have chosen monpoke out", exception.getMessage());
    }

    @Test
    public void whenThereIsNoChosenMonpokeForSecondTeamWhenExecuteShouldGetAnError() {
        when(currentTeam.getChosenMonpoke()).thenReturn(new Monpoke("monpokeName",5,5));
        when(opposingTeam.getChosenMonpoke()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            healCommand.execute(currentTeam, opposingTeam);
        });

        assertEquals("Rule violation - both teams did not have chosen monpoke out", exception.getMessage());
    }

    @Test
    public void cannotCreateHealCommandWithMoreThan2Parameters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new HealCommand(new String[]{"30", "extraParameter"});
        });

        assertEquals("Expecting 1 parameters for command but were received 2", exception.getMessage());
    }

    @Test
    public void cannotCreateHealCommandWithoutHealAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new HealCommand(new String[]{});
        });

        assertEquals("Expecting 1 parameters for command but were received 0", exception.getMessage());
    }
}
