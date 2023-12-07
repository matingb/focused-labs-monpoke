package com.monpoke;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Team {
    private final String teamName;
    private Monpoke chosenMonpoke;
    private final Map<String, Monpoke> ownedMonpoke;
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
        Monpoke monpoke = ownedMonpoke.get(monName);

        if (monpoke == null) {
            throw new IllegalArgumentException("Rule violation - Team " + teamName + " does not own the chosen Monpoke " + monName);
        }

        if (monpoke.getCurrentHealth() < 1) {
            throw new IllegalArgumentException("Rule violation - cannot choose a fainted monpoke");
        }

        this.chosenMonpoke = monpoke;
    }

    public int healMonpoke(String monName, int healAmount) {
        Monpoke mon = ownedMonpoke.get(monName);
        if (mon == null) {
            throw new IllegalArgumentException("Rule violation - cannot heal a monpoke that is not in the team");
        }

        if (mon.getCurrentHealth() < 1) {
            throw new IllegalArgumentException("Rule violation - cannot heal a fainted monpoke");
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

    public void removeMonpokeFromBattle() {
        this.chosenMonpoke = null;
    }
}
