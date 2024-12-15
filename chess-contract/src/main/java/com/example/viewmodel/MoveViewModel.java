package com.example.viewmodel;

import java.time.LocalDateTime;

public record MoveViewModel(
        int moveNumber,
        String playerName,
        String figureType,
        String fromPosition,
        String toPosition,
        String eliminatedFigure,
        LocalDateTime moveTime,
        long durationMove
) {}
