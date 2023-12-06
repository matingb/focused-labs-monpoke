package com.monpoke;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public int getNumAliveMonpoke() {
        return (int) ownedMonpoke.entrySet().stream().filter(monpokeEntry -> monpokeEntry.getValue().getCurrentHealth() > 0).count();
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

    public int healMonpoke(String monName, int healAmount) {
        Monpoke mon = ownedMonpoke.get(monName);
        if (mon == null) {
            throw new IllegalArgumentException("Rule violation - cannot heal a monpoke that is not in the team");
        }

        if (mon.getCurrentHealth() < 1) {
            throw new IllegalArgumentException("Rule violation - cannot heal a monpoke with a current HP of less than 1");
        }

        return mon.receiveHeal(healAmount);
    }

    public void reviveMonpoke(String monName) {
        Monpoke mon = ownedMonpoke.get(monName);
        if (mon == null) {
            throw new IllegalArgumentException("Rule violation - cannot revive a monpoke that is not in the team");
        }

        if (reviveCounter < 1) {
            throw new IllegalArgumentException("Rule violation - each team only gets 1 revive per match");
        }

        if(mon.getCurrentHealth() > 0) {
            throw new IllegalArgumentException("Rule violation - cannot revive an alive monpoke");
        }

        this.reviveCounter--;
        mon.receiveHeal(mon.getMaxHealth());
    }

    public boolean didMonpokeFaint() {
        return chosenMonpoke.getCurrentHealth() <= 0;
    }
}
