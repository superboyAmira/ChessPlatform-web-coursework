package com.example.input;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class GameInputModel {
    private UUID id;

    @NotNull(message = "Игрок 1 обязателен")
    private UUID player1Id;

    @NotNull(message = "Игрок 2 обязателен")
    private UUID player2Id;

    private String result;

    @NotBlank(message = "Тип игры обязателен")
    private String gameType; // Блиц, Рапид, Пуля

    @NotNull(message = "Время начала обязательно")
    private LocalDateTime startTime;

    @Min(value = 1, message = "Duration must be non-negative")
    private long duration;

    public GameInputModel() {
    }

    public GameInputModel(UUID id, UUID player1Id, long duration, LocalDateTime startTime, String gameType, String result, UUID player2Id) {
        this.id = id;
        this.player1Id = player1Id;
        this.duration = duration;
        this.startTime = startTime;
        this.gameType = gameType;
        this.result = result;
        this.player2Id = player2Id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
