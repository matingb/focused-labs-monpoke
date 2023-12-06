package com.monpoke.commands;

import com.monpoke.Monpoke;
import com.monpoke.Team;

public class HealCommand extends Command{
    @Override
    public String execute(String[] commandParameters, Team currentTeam, Team opposingTeam) {
        Monpoke attackingMonpoke = currentTeam.getChosenMonpoke();
        Monpoke attackedMonpoke = opposingTeam.getChosenMonpoke();
        if (attackingMonpoke == null || attackedMonpoke == null) {
            throw new IllegalArgumentException("Rule violation - both teams did not have chosen monpoke out");
        }

        int healAmount = Integer.parseInt(commandParameters[1]);
        int amountHealed = currentTeam.healMonpoke(attackingMonpoke.getName(), healAmount);

        return attackingMonpoke.getName() + " healed for " + amountHealed + " to " + attackingMonpoke.getCurrentHealth();
    }
}
