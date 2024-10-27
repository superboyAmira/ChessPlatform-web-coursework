package ru.chessplatform.domain.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class Player extends BaseEntity {
    private String name;
    private String email;
    private int rating;
    private int gamesPlayed;
    private int gamesWon;
    private String chessGrade; // Гроссмейстер/1 юношеский разряд

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    @Column(name = "rating", nullable = true)
    public int getRating() {
        return rating;
    }

    @Column(name = "games_played", nullable = false)
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    @Column(name = "games_won", nullable = false)
    public int getGamesWon() {
        return gamesWon;
    }

    @Column(name = "chess_grade", nullable = false)
    public String getChessGrade() {
        return chessGrade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setChessGrade(String chessGrade) {
        this.chessGrade = chessGrade;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void incrementGamesWon() {
        this.gamesWon++;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", rating=" + rating +
                ", gamesPlayed=" + gamesPlayed +
                ", gamesWon=" + gamesWon +
                ", chessGrade='" + chessGrade + '\'' +
                '}';
    }
}
