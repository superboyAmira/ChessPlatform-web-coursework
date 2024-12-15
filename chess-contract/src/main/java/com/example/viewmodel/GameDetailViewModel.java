package com.example.viewmodel;

import java.time.LocalDateTime;
import java.util.List;

public record GameDetailViewModel(
        PlayerViewModel player1,
        PlayerViewModel player2,
        String result, // "Победа", "Ничья", "Поражение"
        String gameType, // "Блиц", "Классика"
        LocalDateTime startTime,
        long duration,
        List<MoveViewModel> moves
) {
}
