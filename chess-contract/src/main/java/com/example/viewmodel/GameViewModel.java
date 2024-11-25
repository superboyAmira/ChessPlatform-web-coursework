package com.example.viewmodel;

import com.example.viewmodel.PlayerViewModel;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameViewModel(
        UUID id,
        PlayerViewModel player1Id,
        PlayerViewModel player2Id,
        String result, // "Победа", "Ничья", "Поражение"
        String gameType, // "Блиц", "Классика"
        LocalDateTime startTime,
        long duration
) {}