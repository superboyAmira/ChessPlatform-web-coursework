package com.example.viewmodel;

import java.util.UUID;

public class TournamentEntryViewModel {
    private UUID playerId;
    private String playerName;
    private int points;
    private int gamesPlayed;

    public TournamentEntryViewModel(UUID playerId, String playerName, int points, int gamesPlayed) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPoints() {
        return points;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    @Override
    public String toString() {
        return "TournamentEntryViewModel{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", points=" + points +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
}
