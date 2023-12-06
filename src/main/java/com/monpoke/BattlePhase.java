package com.monpoke;

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
     * @param battleCommands Must be an ICHOOSEYOU or ATTACK command.
     *                       ATTACK will ignore any parameters,
     *                       ICHOOSEYOU must have the parameters:
     *                       ICHOOSEYOU <mon-name>
     * @return the output for the ICHOOSEYOU or ATTACK event
     */
    public String battle(String[] battleCommands) {
        if (teams == null) {
            throw new IllegalArgumentException("Rule violation - battle has not been started");
        }

        Team currentTeam = teams[turnCounter % 2];
        Team opposingTeam = teams[1 - (turnCounter % 2)];
        turnCounter++;
        if (battleCommands[0].equals("ICHOOSEYOU")) {
            String monName = battleCommands[1];
            currentTeam.chooseMonpoke(monName);
            return monName + " has entered the battle!";
        } else if (battleCommands[0].equals("ATTACK")) { // Could split out into another method if any more complicated
            Monpoke attackingMonpoke = currentTeam.getChosenMonpoke();
            Monpoke attackedMonpoke = opposingTeam.getChosenMonpoke();
            if (attackingMonpoke == null || attackedMonpoke == null) {
                throw new IllegalArgumentException("Rule violation - both teams did not have chosen monpoke out");
            }

            attackedMonpoke.receiveAttack(attackingMonpoke);

            String commandOutput = attackingMonpoke.getName() + " attacked " + attackedMonpoke.getName()
                    + " for " + attackingMonpoke.getAttack() + " damage!";

            if (opposingTeam.didMonpokeFaint()) {
                commandOutput += "\n";
                commandOutput += attackedMonpoke.getName() + " has been defeated!";
                if (opposingTeam.getNumMonpoke() == 0) {
                    winner = currentTeam;
                }
            }

            return commandOutput;
        } else if (battleCommands[0].equals("HEAL")) {
            Monpoke attackingMonpoke = currentTeam.getChosenMonpoke();
            Monpoke attackedMonpoke = opposingTeam.getChosenMonpoke();
            if (attackingMonpoke == null || attackedMonpoke == null) {
                throw new IllegalArgumentException("Rule violation - both teams did not have chosen monpoke out");
            }

            int healAmount = Integer.parseInt(battleCommands[1]);
            int amountHealed = attackingMonpoke.heal(healAmount);

            return attackingMonpoke.getName() + " healed for " + amountHealed + " to " + attackingMonpoke.getCurrentHealth();
        }

        return "Unreadable attack output";
    }

    public Team getWinner() {
        return winner;
    }

    protected int getTurnCounter() {
        return turnCounter;
    }
}
