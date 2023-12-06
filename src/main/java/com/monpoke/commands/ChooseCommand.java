package com.monpoke.commands;

import com.monpoke.Team;

public class ChooseCommand extends Command{
    @Override
    public String execute(String[] commandParameters, Team currentTeam, Team opposingTeam) {
        String monName = commandParameters[1];
        currentTeam.chooseMonpoke(monName);
        return monName + " has entered the battle!";
    }
}
