package com.monpoke.commands.battleCommands;

import com.monpoke.Monpoke;
import com.monpoke.Team;

public class HealCommand extends BattleCommand {

    private final int healAmount;

    public HealCommand(String[] commandParameters) {
        validateParametersSize(commandParameters, 1);
        healAmount = Integer.parseInt(commandParameters[0]);
    }

    @Override
    public String execute(Team currentTeam, Team opposingTeam) {
        Monpoke attackingMonpoke = currentTeam.getChosenMonpoke();
        Monpoke attackedMonpoke = opposingTeam.getChosenMonpoke();
        if (attackingMonpoke == null || attackedMonpoke == null) {
            throw new IllegalArgumentException("Rule violation - both teams did not have chosen monpoke out");
        }

        int amountHealed = currentTeam.healMonpoke(attackingMonpoke.getName(), healAmount);

        return attackingMonpoke.getName() + " healed for " + amountHealed + " to " + attackingMonpoke.getCurrentHealth();
    }
}
