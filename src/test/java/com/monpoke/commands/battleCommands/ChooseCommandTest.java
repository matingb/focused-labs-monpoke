package com.monpoke.commands.battleCommands;

import com.monpoke.commands.battleCommands.ChooseCommand;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ChooseCommandTest {
    @Test
    public void cannotCreateChooseCommandWithMoreThan2Parameters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ChooseCommand(new String[]{"monToChoose", "extraParameter"});
        });

        assertEquals("Expecting 1 parameters for command but were received 2", exception.getMessage());
    }

    @Test
    public void cannotCreateChooseCommandWithoutHealAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ChooseCommand(new String[]{});
        });

        assertEquals("Expecting 1 parameters for command but were received 0", exception.getMessage());
    }
}
