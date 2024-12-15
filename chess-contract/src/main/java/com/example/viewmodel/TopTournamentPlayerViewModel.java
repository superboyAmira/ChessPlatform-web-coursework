package com.example.viewmodel;

import java.util.UUID;

public record TopTournamentPlayerViewModel (
        UUID id,
        String name,
        String chessGrade,
        Integer successScore
){}
