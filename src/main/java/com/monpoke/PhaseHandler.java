package com.monpoke;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PhaseHandler {

    private static final String DELIMITER = " ";

    /**
     * Handles running through the game and creating the output
     * @param commandScanner Scanner that will feed the game commands line by line
     * @param createPhase Start of a new create phase
     * @param battlePhase Start of a new battle phase
     * @return output of the battle results
     */
    public ArrayList<String> runGame(Scanner commandScanner, CreatePhase createPhase, BattlePhase battlePhase) {
        ArrayList<String> outputStrings = new ArrayList<>();
        boolean inBattle = false;

        while (battlePhase.getWinner() == null) {
            String nextCommand = "";
            try{
                nextCommand = commandScanner.nextLine();
            } catch(NoSuchElementException e){
                throw new IllegalArgumentException("Input file has reached its end without a winner");
            }

            String[] commandArgs = nextCommand.split(DELIMITER);

            switch (commandArgs[0]) {
                case "CREATE":
                    if (inBattle) {
                        throw new IllegalArgumentException("Rule violated - battle is already in progress");
                    }
                    outputStrings.add(createPhase.create(commandArgs));
                    break;

                case "ICHOOSEYOU":
                    // Initiate battle on first ICHOOSEYOU, no fall through intentionally
                    if (!inBattle) {
                        if (!createPhase.readyToBattle()) {
                            throw new IllegalArgumentException("Rule violated - teams not created");
                        }
                        battlePhase.startBattle(createPhase.getTeams());
                        inBattle = true;
                    }
                    outputStrings.add(battlePhase.battle(commandArgs));
                    break;

                case "ATTACK":
                case "HEAL":
                case "REVIVE":
                    outputStrings.add(battlePhase.battle(commandArgs));
                    break;

                default:
                    throw new IllegalArgumentException("Unreadable command input");
            }
        }

        outputStrings.add(battlePhase.getWinner().getTeamName() + " is the winner!");
        return outputStrings;
    }

}
