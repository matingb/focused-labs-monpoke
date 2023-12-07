package com.monpoke.commands;

import com.monpoke.Team;

public class ReviveCommand extends BattleCommand {

    private final String monToRevive;

    public ReviveCommand(String[] commandParameters) {
        validateParametersSize(commandParameters, 1);
        this.monToRevive = commandParameters[0];
    }

    @Override
    public String execute(Team currentTeam, Team opposingTeam) {
        currentTeam.reviveMonpoke(monToRevive);

        String commandOutput = monToRevive + " has been revived";

        if (currentTeam.getChosenMonpoke() == null) {
            commandOutput += "\n";
            BattleCommand command = new ChooseCommand(new String[]{ monToRevive });
            commandOutput += command.execute(currentTeam, opposingTeam);
        }

        return commandOutput;
    }
}
