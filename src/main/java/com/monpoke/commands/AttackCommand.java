package com.monpoke.commands;

import com.monpoke.Monpoke;
import com.monpoke.Team;

public class AttackCommand extends Command{
    @Override
    public String execute(String[] commandParameters, Team currentTeam, Team opposingTeam) {
        Monpoke attackingMonpoke = currentTeam.getChosenMonpoke();
        Monpoke attackedMonpoke = opposingTeam.getChosenMonpoke();
        if (attackingMonpoke == null || attackedMonpoke == null) {
            throw new IllegalArgumentException("Rule violation - both teams did not have chosen monpoke out");
        }

        attackedMonpoke.receiveAttack(attackingMonpoke);

        String commandOutput = attackingMonpoke.getName() + " attacked " + attackedMonpoke.getName()
                + " for " + attackingMonpoke.getAttack() + " damage!";

        if (opposingTeam.didMonpokeFaint()) {
            opposingTeam.removeMonpokeFromBattle();
            commandOutput += "\n";
            commandOutput += attackedMonpoke.getName() + " has been defeated!";
        }

        return commandOutput;
    }
}
