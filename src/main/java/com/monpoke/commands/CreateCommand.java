package com.monpoke.commands;

public class CreateCommand extends Command {

    private final String teamName;
    private final String monName;
    private final int hp;
    private final int attack;

    public CreateCommand(String[] commandParameters) {
        validateParametersSize(commandParameters, 4);
        this.teamName = commandParameters[0];
        this.monName = commandParameters[1];
        this.hp = Integer.parseInt(commandParameters[2]);
        this.attack = Integer.parseInt(commandParameters[3]);
    }

    public String getTeamName() {
        return teamName;
    }

    public String getMonName() {
        return monName;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }
}
