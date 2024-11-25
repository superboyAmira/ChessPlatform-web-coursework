package com.example.viewmodel;
import java.time.LocalDateTime;
import java.util.UUID;

public record TournamentViewModel(
        UUID id,
        String name,
        LocalDateTime startDate,
        int participantCount,
        String tournamentType,
        double prizePool
) {}