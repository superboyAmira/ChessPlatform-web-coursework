package com.example.input;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TournamentInputModel {
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @Min(value = 2, message = "Participants must be at least 2")
    private int participantCount;

    @NotBlank(message = "Tournament type is required")
    private String tournamentType;

    @DecimalMin(value = "0.0", inclusive = true, message = "Prize pool must be non-negative")
    private Double prizePool;

    public TournamentInputModel(UUID id, int participantCount, LocalDateTime startDate, String name, String tournamentType, double prizePool) {
        this.id = id;
        this.participantCount = participantCount;
        this.startDate = startDate;
        this.name = name;
        this.tournamentType = tournamentType;
        this.prizePool = prizePool;
    }

    public TournamentInputModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public Double getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(Double prizePool) {
        this.prizePool = prizePool;
    }
}
