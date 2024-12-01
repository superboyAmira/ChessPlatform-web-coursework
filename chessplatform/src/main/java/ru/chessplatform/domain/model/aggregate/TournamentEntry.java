package ru.chessplatform.domain.model.aggregate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.chessplatform.domain.model.entity.BaseEntity;
import ru.chessplatform.domain.model.entity.Player;

@Entity
@Table(name = "tournament_entry")
public class TournamentEntry extends BaseEntity {
    private Tournament tournament;
    private Player player;
    private int points;
    private int gamesPlayed;

    public TournamentEntry() {}

    public TournamentEntry(Player player, int points, int gamesPlayed) {
        this.player = player;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
    }


    public TournamentEntry(Tournament tournament, Player player, int points, int gamesPlayed) {
        this.tournament = tournament;
        this.player = player;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
    }

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    public Tournament getTournament() {
        return tournament;
    }

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    public Player getPlayer() {
        return player;
    }

    @Column(name = "points", nullable = false)
    public int getPoints() {
        return points;
    }

    @Column(name = "games_played", nullable = false)
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void addPoints(int additionalPoints) {
        this.points += additionalPoints;
    }

    @Override
    public String toString() {
        return "TournamentEntry{" +
                "tournament=" + tournament.getName() +
                ", player=" + player.getName() +
                ", points=" + points +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
}
