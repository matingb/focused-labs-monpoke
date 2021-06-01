package com.monpoke;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;

public class GameRunnerTest {
    // Using SystemRules dependency for system exit testing
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void fileScannerTypeNotFound(){
        exit.expectSystemExitWithStatus(1);
        String[] inputArgs = new String[]{"nonexistingfile"};
        GameRunner.getScannerType(inputArgs);
    }

    @Test
    public void inputScannerType(){
        String[] inputArgs = new String[0];
        GameRunner.getScannerType(inputArgs);
        assertEquals("No file input found. Start inputting commands manually!", systemOutRule.getLog().trim());
    }

}