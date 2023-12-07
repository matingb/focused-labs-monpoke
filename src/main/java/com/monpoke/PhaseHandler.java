package com.monpoke;

import com.monpoke.commands.battleCommands.BattleCommand;
import com.monpoke.commands.CommandName;
import com.monpoke.commands.CreateCommand;

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
    public void runGame(Scanner commandScanner, CreatePhase createPhase, BattlePhase battlePhase, Logger logger) {
        ArrayList<String> outputStrings = new ArrayList<>();

        String[] commandArgs = getNextCommand(commandScanner);

        do {
            CreateCommand createCommand = getCreateCommand(commandArgs);
            logger.info(createPhase.create(createCommand));
            commandArgs = getNextCommand(commandScanner);
        } while ("CREATE".equals(commandArgs[0]));

        battlePhase.startBattle(createPhase.getTeams());

        while (battlePhase.getWinner() == null) {
            BattleCommand battleCommand = getBattleCommand(commandArgs);
            logger.info(battlePhase.battle(battleCommand));

            if (battlePhase.getWinner() == null) {
                commandArgs = getNextCommand(commandScanner);
            }
        }

        logger.info(battlePhase.getWinner().getTeamName() + " is the winner!");
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

            if(battleCommand == null) {
                throw new IllegalArgumentException("Unreadable battle command input");
            }
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Unreadable battle command input");
        }

        return battleCommand;
    }

    private CreateCommand getCreateCommand(String[] createParameters) {

        CommandName commandName;
        try {
           commandName = CommandName.valueOf(createParameters[0]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The first commands must be CREATE commands");
        }

        if (!CommandName.CREATE.equals(commandName)) {
            throw new IllegalArgumentException("The first commands must be CREATE commands");
        }

        createParameters = Arrays.copyOfRange(createParameters, 1, createParameters.length);

        return new CreateCommand(createParameters);
    }

}
