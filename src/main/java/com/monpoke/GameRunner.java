package com.monpoke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/* Main class to be used for input and phase handling */
public class GameRunner {

    public static void main(String[] args) {
        Scanner commandScanner = getScannerType(args);

        CreatePhase createPhase = new CreatePhase();
        BattlePhase battlePhase = new BattlePhase();
        PhaseHandler phaseHandler = new PhaseHandler();
        Logger logger = new Logger();

        try {
            phaseHandler.runGame(commandScanner, createPhase, battlePhase, logger);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }

        commandScanner.close();
    }

    /**
     * Gets the proper scanner for the game to use.
     *
     * @param args input arguments that may or may not contain a file path
     * @return a scanner for a file if provided, otherwise a user input scanner
     */
    protected static Scanner getScannerType(String[] args) {
        if (args.length >= 1) {
            System.out.println("File input found. Using file to run the game.");
            System.out.println(args[0]);
            String input = args[0];
            try {
                return new Scanner(new File(input));
            } catch (FileNotFoundException e) {
                System.out.println("Specified input file was not found");
                System.exit(1);
            }
        }

        System.out.println("No file input found. Start inputting commands manually!");
        return new Scanner(System.in);
    }
}
