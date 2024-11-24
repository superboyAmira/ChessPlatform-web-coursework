package com.example.trash;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class GameCreateInputModel {

    @NotNull(message = "Игрок 1 обязателен")
    private UUID player1Id;

    @NotNull(message = "Игрок 2 обязателен")
    private UUID player2Id;

    @NotBlank(message = "Тип игры обязателен")
    private String gameType; // Блиц, Рапид, Пуля

    @NotNull(message = "Время начала обязательно")
    private LocalDateTime startTime;

    // Геттеры и сеттеры
    // ...
}
