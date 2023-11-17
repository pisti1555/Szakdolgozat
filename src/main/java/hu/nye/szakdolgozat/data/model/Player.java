package hu.nye.szakdolgozat.data.model;

import java.util.UUID;

public class Player {
    private UUID id;
    private String username, irlName;
    private int playedGames, wonGames, stepsMade;

    public Player(UUID id, String username, String irlName, int playedGames, int wonGames, int stepsMade) {
        this.id = id;
        this.username = username;
        this.irlName = irlName;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.stepsMade = stepsMade;
    }

    public Player(String username, String irlName, int playedGames, int wonGames, int stepsMade) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.irlName = irlName;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.stepsMade = stepsMade;
    }

    public Player() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIrlName() {
        return irlName;
    }

    public void setIrlName(String irlName) {
        this.irlName = irlName;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getStepsMade() {
        return stepsMade;
    }

    public void setStepsMade(int stepsMade) {
        this.stepsMade = stepsMade;
    }
}
