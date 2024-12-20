package ru.chessplatform.domain.model.aggregate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ru.chessplatform.domain.model.entity.BaseEntity;

@Entity
@Table(name = "tournament")
public class Tournament extends BaseEntity implements Serializable {
    private String name;
    private LocalDateTime startDate;
    private int participantCount;
    private String tournamentType;
    private double prizePool;
    private List<TournamentEntry> entries = new ArrayList<>();
    private boolean closed;

    public Tournament() {}

    public Tournament(String name, LocalDateTime startDate, int participantCount, String tournamentType, double prizePool) {
        this.name = name;
        this.startDate = startDate;
        this.participantCount = participantCount;
        this.tournamentType = tournamentType;
        this.prizePool = prizePool;
        this.closed = false;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "start_date", nullable = false)
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Column(name = "participant_count", nullable = false)
    public int getParticipantCount() {
        return participantCount;
    }

    @Column(name = "tournament_type", nullable = false)
    public String getTournamentType() {
        return tournamentType;
    }

    @Column(name = "prize_pool")
    public double getPrizePool() {
        return prizePool;
    }

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    public List<TournamentEntry> getEntries() {
        return entries;
    }

    @Column(name = "closed")
    public boolean  getStatus() {
        return this.closed;
    }

    public void setClosedStatus() {
        this.closed = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public void setTournamentType(String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public void setPrizePool(double prizePool) {
        this.prizePool = prizePool;
    }

    public void setEntries(List<TournamentEntry> entries) {
        this.entries = entries;
    }

    public void setStatus(boolean closed) {
        this.closed = closed;
    }

    // TODO: вероятно нужно проидывать не класс TournamentEntry а составные его части
    public void addEntry(TournamentEntry entry) {
        entries.add(entry);
        entry.setTournament(this);
    }
    public void removeEntry(TournamentEntry entry) {
        entries.remove(entry);
        participantCount--;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", participantCount=" + participantCount +
                ", tournamentType='" + tournamentType + '\'' +
                ", prizePool=" + prizePool +
                ", entries=" + entries +
                '}';
    }
}
