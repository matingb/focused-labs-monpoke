package com.monpoke.commands;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CreateCommandTest {

    @Test
    public void cannotCreateCreateCommandWithMoreThan4Parameters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateCommand(new String[]{"Rocket", "Meekachu", "3", "1", "8"});
        });

        assertEquals("Expecting 4 parameters for command but were received 5", exception.getMessage());
    }

    @Test
    public void cannotCreateCreateCommandWithLessThan4Parameters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateCommand(new String[]{"Rocket", "Meekachu", "3"});
        });

        assertEquals("Expecting 4 parameters for command but were received 3", exception.getMessage());
    }
}
