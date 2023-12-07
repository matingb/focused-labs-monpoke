package com.monpoke;

import com.monpoke.commands.CreateCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreatePhaseTest {
    CreatePhase createPhase;

    CreateCommand testCreate = new CreateCommand(new String[]{"Rocket", "Meekachu", "3", "1"});
    CreateCommand testCreate2 = new CreateCommand(new String[]{"Rocket", "Rastly", "5", "6"});
    CreateCommand testCreate3 = new CreateCommand(new String[]{"Green", "Smorelax", "2", "1"});
    CreateCommand testCreate4 = new CreateCommand(new String[]{"Magma", "Smorelax", "2", "1"});

    @Before
    public void setUp() {
        createPhase = new CreatePhase();
    }

    @Test
    public void successfulSingleOutput() {
        String output = createPhase.create(testCreate);
        assertEquals("Meekachu has been assigned to team Rocket!", output);
    }

    @Test
    public void multiplesForTeams() {
        String firstOutput = createPhase.create(testCreate);
        String secondOutput = createPhase.create(testCreate2);
        String thirdOutput = createPhase.create(testCreate3);

        assertEquals("Meekachu has been assigned to team Rocket!", firstOutput);
        assertEquals("Rastly has been assigned to team Rocket!", secondOutput);
        assertEquals("Smorelax has been assigned to team Green!", thirdOutput);

        Team[] createdTeams = createPhase.getTeams();
        assertEquals("Rocket", createdTeams[0].getTeamName());
        assertEquals("Green", createdTeams[1].getTeamName());
    }

    @Test
    public void multiplesForTeamsSwapOrder() {
        String firstOutput = createPhase.create(testCreate);
        String secondOutput = createPhase.create(testCreate3);
        String thirdOutput = createPhase.create(testCreate2);

        assertEquals("Meekachu has been assigned to team Rocket!", firstOutput);
        assertEquals("Smorelax has been assigned to team Green!", secondOutput);
        assertEquals("Rastly has been assigned to team Rocket!", thirdOutput);

        Team[] createdTeams = createPhase.getTeams();
        assertEquals("Rocket", createdTeams[0].getTeamName());
        assertEquals("Green", createdTeams[1].getTeamName());
    }

    @Test
    public void tooManyTeams() {
        createPhase.create(testCreate);
        createPhase.create(testCreate3);
        try {
            createPhase.create(testCreate4);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Rule violation - attempting to add more than 2 teams", e.getMessage());
        }
    }

    @Test
    public void readyToBattle(){
        createPhase.create(testCreate);
        createPhase.create(testCreate3);

        assertTrue(createPhase.readyToBattle());

        CreatePhase unfinishedPhase = new CreatePhase();
        unfinishedPhase.create(testCreate);
        assertFalse(unfinishedPhase.readyToBattle());
    }
}