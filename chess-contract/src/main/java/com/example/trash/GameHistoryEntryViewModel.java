package com.example.trash;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameHistoryEntryViewModel(
        UUID gameId,
        LocalDateTime dateTime,
        UUID opponentId,
        String opponentName,
        String result, // "Победа", "Ничья", "Поражение"
        String gameType, // "Блиц", "Классика"
        int ratingChange,
        int moveCount
) {}