package com.monpoke.commands;

import com.monpoke.Team;

public abstract class Command {
     public static Command create(String commandName) {
        switch (commandName) {
            case "ICHOOSEYOU":
                return new ChooseCommand();
            case "ATTACK":
                return new AttackCommand();
            case "HEAL":
                return new HealCommand();
            case "REVIVE":
                return new ReviveCommand();
            default:
                return null;
        }
    }

    public abstract String execute(String[] commandParameters, Team currentTeam, Team opposingTeam);
}
