package com.monpoke.commands;

import com.monpoke.Team;

import java.util.Arrays;

public abstract class BattleCommand {
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

    protected void validateParametersSize(String[] commandParameters, int expectedSize) {
        if (commandParameters.length != expectedSize) {
            throw new IllegalArgumentException("Expecting " + expectedSize + " parameters for command but were received " + commandParameters.length);
        }
    }
}
