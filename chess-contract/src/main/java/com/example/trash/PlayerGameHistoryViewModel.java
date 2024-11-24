package com.example.trash;

import java.util.List;
import java.util.UUID;

// 2 страница
public record PlayerGameHistoryViewModel(
        BaseViewModel base,
        UUID playerId,
        String playerName,
        List<GameHistoryEntryViewModel> gameHistory
) {}