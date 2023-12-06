package com.monpoke.commands;

import com.monpoke.Team;

public class ReviveCommand extends Command{
    @Override
    public String execute(String[] commandParameters, Team currentTeam, Team opposingTeam) {
        String monToRevive = commandParameters[1];
        currentTeam.reviveMonpoke(monToRevive);

        String commandOutput = monToRevive + " has been revived";

        if (currentTeam.getChosenMonpoke() == null) {
            commandOutput += "\n";
            Command command = new ChooseCommand();
            commandOutput += command.execute(commandParameters, currentTeam, opposingTeam);
        }

        return commandOutput;
    }
}
