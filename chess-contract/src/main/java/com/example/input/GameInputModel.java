package com.example.input;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class GameInputModel {

    @NotNull(message = "Игрок 1 обязателен")
    private UUID player1Id;

    @NotNull(message = "Игрок 2 обязателен")
    private UUID player2Id;

    @NotBlank(message = "Тип игры обязателен")
    private String gameType; // Блиц, Рапид, Пуля

    @NotNull(message = "Время начала обязательно")
    private LocalDateTime startTime;

    public GameInputModel(UUID player1Id, UUID player2Id, String gameType, LocalDateTime startTime) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.gameType = gameType;
        this.startTime = startTime;
    }

    public UUID getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(UUID player1Id) {
        this.player1Id = player1Id;
    }

    public UUID getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(UUID player2Id) {
        this.player2Id = player2Id;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
