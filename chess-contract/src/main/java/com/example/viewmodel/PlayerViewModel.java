package com.example.viewmodel;

import java.util.UUID;

public record PlayerViewModel(
        UUID id,
        String name,
        int rating,
        String chessGrade
) {}