package com.example.viewmodel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TournamentViewModel {
    private UUID id;
    private String name;
    private LocalDateTime startDate;
    private int participantCount;
    private String tournamentType;
    private double prizePool;
    private boolean closed;
    private List<TournamentEntryViewModel> entries;

    public TournamentViewModel(UUID id, String name, LocalDateTime startDate, int participantCount,
                               String tournamentType, double prizePool, boolean closed,
                               List<TournamentEntryViewModel> entries) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.participantCount = participantCount;
        this.tournamentType = tournamentType;
        this.prizePool = prizePool;
        this.closed = closed;
        this.entries = entries;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public double getPrizePool() {
        return prizePool;
    }

    public boolean isClosed() {
        return closed;
    }

    public List<TournamentEntryViewModel> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "TournamentViewModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", participantCount=" + participantCount +
                ", tournamentType='" + tournamentType + '\'' +
                ", prizePool=" + prizePool +
                ", closed=" + closed +
                ", entries=" + entries +
                '}';
    }
}
