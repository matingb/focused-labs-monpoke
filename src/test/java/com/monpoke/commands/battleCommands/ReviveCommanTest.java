package com.monpoke.commands.battleCommands;

import com.monpoke.commands.battleCommands.ReviveCommand;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ReviveCommanTest {
    @Test
    public void cannotCreateReviveCommandWithMoreThan2Parameters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReviveCommand(new String[]{"monToRevive", "extraParameter"});
        });

        assertEquals("Expecting 1 parameters for command but were received 2", exception.getMessage());
    }

    @Test
    public void cannotCreateReviveCommandWithoutHealAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReviveCommand(new String[]{});
        });

        assertEquals("Expecting 1 parameters for command but were received 0", exception.getMessage());
    }
}
