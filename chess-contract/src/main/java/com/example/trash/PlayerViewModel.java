package com.example.trash;

import java.util.UUID;

public record PlayerViewModel(
        UUID id,
        String name,
        int rating,
        String chessGrade
) {}