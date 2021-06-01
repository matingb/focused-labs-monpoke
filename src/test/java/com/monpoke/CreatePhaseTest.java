package com.monpoke;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreatePhaseTest {
    CreatePhase createPhase;
    String[] testCreate = new String[]{"CREATE", "Rocket", "Meekachu", "3", "1"};
    String[] testCreate2 = new String[]{"CREATE", "Rocket", "Rastly", "5", "6"};
    String[] testCreate3 = new String[]{"CREATE", "Green", "Smorelax", "2", "1"};
    String[] testCreate4 = new String[]{"CREATE", "Magma", "Smorelax", "2", "1"};

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