package com.monpoke;

import com.monpoke.commands.BattleCommand;
import com.monpoke.commands.CommandName;

import java.util.*;

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

        String[] commandArgs = getNextCommand(commandScanner);

        if(!"CREATE".equals(commandArgs[0])) {
            throw new IllegalArgumentException("Unreadable command input");
        }

        while ("CREATE".equals(commandArgs[0])) {
            outputStrings.add(createPhase.create(commandArgs));
            commandArgs = getNextCommand(commandScanner);
        }

        battlePhase.startBattle(createPhase.getTeams());

        while (battlePhase.getWinner() == null) {
            BattleCommand battleCommand = getBattleCommand(commandArgs);
            outputStrings.add(battlePhase.battle(battleCommand));

            if (battlePhase.getWinner() == null) {
                commandArgs = getNextCommand(commandScanner);
            }
        }

        outputStrings.add(battlePhase.getWinner().getTeamName() + " is the winner!");
        return outputStrings;
    }

    private String[] getNextCommand(Scanner commandScanner) {
        try {
            return commandScanner.nextLine().split(DELIMITER);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Input file has reached its end without a winner");
        }
    }

    private BattleCommand getBattleCommand(String[] battleCommands) {

        BattleCommand battleCommand;

        try {
            CommandName commandName = CommandName.valueOf(battleCommands[0]);
            battleCommands = Arrays.copyOfRange(battleCommands, 1, battleCommands.length);
            battleCommand = BattleCommand.create(commandName, battleCommands);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Unreadable battle command input");
        }

        return battleCommand;
    }

}
