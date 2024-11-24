package com.example.trash;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameViewModel(
        UUID id,
        UUID player1Id,
        String player1Name,
        UUID player2Id,
        String player2Name,
        String result, // "Победа", "Ничья", "Поражение"
        String gameType, // "Блиц", "Классика"
        LocalDateTime startTime,
        long duration
) {}