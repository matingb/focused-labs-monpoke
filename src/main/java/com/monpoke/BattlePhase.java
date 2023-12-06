package com.monpoke;

import com.monpoke.commands.Command;

/* Create phase handler for the battle phase of the game */
public class BattlePhase {

    private Team[] teams;

    private int turnCounter = 0;
    private Team winner = null;

    public void startBattle(Team[] teams) {
        this.teams = teams;
    }

    /**
     * Processes a battle command. Handles validation for all battle rules.
     * The battle phase will always start with the team first in order.
     *
     * @param battleCommands Must be an ICHOOSEYOU | ATTACK | HEAL | REVIVE command.
     *                       ATTACK will ignore any parameters,
     *                       ICHOOSEYOU must have the parameters:
     *                       ICHOOSEYOU <mon-name>
     *                       HEAL must have the parameters:
     *                       HEAL <heal-amount>
     *                       REVIVE must have the parameters:
     *                       REVIVE <mon-name>
     * @return the output for the ICHOOSEYOU | ATTACK | HEAL | REVIVE event
     */
    public String battle(String[] battleCommands) {
        if (teams == null) {
            throw new IllegalArgumentException("Rule violation - battle has not been started");
        }

        Team currentTeam = teams[turnCounter % 2];
        Team opposingTeam = teams[1 - (turnCounter % 2)];
        turnCounter++;

        Command command = Command.create(battleCommands[0]);
        if (command == null) {
            return "Unreadable attack output";
        }

        String commandOutput = command.execute(battleCommands, currentTeam, opposingTeam);

        if (opposingTeam.getNumAliveMonpoke() == 0) {
            winner = currentTeam;
        }

        return commandOutput;
    }

    public Team getWinner() {
        return winner;
    }

    protected int getTurnCounter() {
        return turnCounter;
    }
}
