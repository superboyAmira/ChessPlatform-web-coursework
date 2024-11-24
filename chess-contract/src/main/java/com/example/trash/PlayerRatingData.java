package com.example.trash;

import java.util.UUID;

public record PlayerRatingData(
        UUID playerId,
        String playerName,
        int rating
) {}