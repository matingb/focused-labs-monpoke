package com.monpoke;

import java.util.HashMap;
import java.util.Map;

public class Team {
    private String teamName;
    private Monpoke chosenMonpoke;
    private Map<String, Monpoke> ownedMonpoke;
    private int reviveCounter = 1;

    Team(String teamName) {
        this.teamName = teamName;
        ownedMonpoke = new HashMap<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public Monpoke getChosenMonpoke() {
        return chosenMonpoke;
    }

    public int getNumMonpoke() {
        return ownedMonpoke.size();
    }

    public void addMonpoke(Monpoke monpoke) {
        ownedMonpoke.put(monpoke.getName(), monpoke);
    }

    public void chooseMonpoke(String monName) {
        this.chosenMonpoke = ownedMonpoke.get(monName);

        if (chosenMonpoke == null) {
            throw new IllegalArgumentException("Rule violation - Team " + teamName + " does not own the chosen Monpoke " + monName);
        }
    }

    public void reviveMonpoke(String monName) {
        if (reviveCounter < 1) {
            throw new IllegalArgumentException("Rule violation - each team only gets 1 revive per match");
        }

        this.reviveCounter--;
    }

    public boolean didMonpokeFaint() {
        if (chosenMonpoke.getCurrentHealth() <= 0) {
            ownedMonpoke.remove(chosenMonpoke.getName());
            chosenMonpoke = null;
            return true;
        }
        return false;
    }
}
