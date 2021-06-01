package com.monpoke;

import java.util.stream.IntStream;

/* Create phase handler for the team creation phase of the game */
public class CreatePhase {

    private Team[] teams = new Team[2];
    private int numTeams = 0;

    /**
     * Processes a create command. Handles validation for too many teams.
     *
     * @param createCommands Must be a CREATE command with the parameters:
     *                       CREATE <team-name> <mon-name> <mon-hp> <mon-attack>
     * @return the output for the CREATE event
     */
    public String create(String[] createCommands) {
        String teamName = createCommands[1];
        String monName = createCommands[2];
        int hp = Integer.parseInt(createCommands[3]);
        int attack = Integer.parseInt(createCommands[4]);
        Monpoke newMon = new Monpoke(monName, hp, attack);
        int indexOfTeam = findTeamIndex(teamName);
        Team monOwner = null;

        if (indexOfTeam == -1) {
            if (numTeams < 2) {
                monOwner = new Team(teamName);
                teams[numTeams] = monOwner;
                numTeams++;
            } else {
                throw new IllegalArgumentException("Rule violation - attempting to add more than 2 teams");
            }
        } else {
            monOwner = teams[indexOfTeam];
        }

        monOwner.addMonpoke(newMon);
        return monName + " has been assigned to team " + teamName + "!";
    }

    public int findTeamIndex(String teamName) {
        return IntStream.range(0, teams.length)
                .filter(i -> teams[i] != null) // avoiding initializing every position
                .filter(i -> teams[i].getTeamName().equals(teamName))
                .findFirst()
                .orElse(-1);
    }

    public boolean readyToBattle() {
        return numTeams == 2;
    }

    public Team[] getTeams() {
        return teams;
    }
}
