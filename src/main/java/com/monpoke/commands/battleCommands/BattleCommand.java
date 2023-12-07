package com.monpoke.commands.battleCommands;

import com.monpoke.Monpoke;
import com.monpoke.Team;
import com.monpoke.commands.Command;
import com.monpoke.commands.CommandName;

public abstract class BattleCommand extends Command {
     public static BattleCommand create(CommandName commandName, String[] commandParameters) {
         switch (commandName) {
            case ICHOOSEYOU:
                return new ChooseCommand(commandParameters);
            case ATTACK:
                return new AttackCommand(commandParameters);
            case HEAL:
                return new HealCommand(commandParameters);
            case REVIVE:
                return new ReviveCommand(commandParameters);
            default:
                return null;
        }
    }

    public abstract String execute(Team currentTeam, Team opposingTeam);
    protected void validateBothTeamsHaveChosenAMonpoke(Monpoke attackingMonpoke, Monpoke attackedMonpoke) {
        if (attackingMonpoke == null || attackedMonpoke == null) {
            throw new IllegalArgumentException("Rule violation - both teams did not have chosen monpoke out");
        }
    }
}
