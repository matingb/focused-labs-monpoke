package com.monpoke.commands;

import com.monpoke.Team;

public class ChooseCommand extends BattleCommand {

    private final String monName;

    public ChooseCommand(String[] commandParameters) {
        validateParametersSize(commandParameters, 1);
        this.monName = commandParameters[0];
    }

    @Override
    public String execute(Team currentTeam, Team opposingTeam) {
        currentTeam.chooseMonpoke(monName);
        return monName + " has entered the battle!";
    }
}
