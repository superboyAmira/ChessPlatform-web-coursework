package com.example.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TournamentEntryInputModel {
    @NotNull(message = "Player ID is required")
    private UUID playerId;

    @Min(value = 0, message = "Points must be at least 0")
    private int points;

    @Min(value = 0, message = "Games played must be at least 0")
    private int gamesPlayed;

    public TournamentEntryInputModel() {}

    public TournamentEntryInputModel(UUID playerId, int points, int gamesPlayed) {
        this.playerId = playerId;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
