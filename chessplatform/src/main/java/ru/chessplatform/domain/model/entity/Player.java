package ru.chessplatform.domain.model.entity;

import ru.chessplatform.application.dto.TopTournamentPlayer;
import ru.chessplatform.domain.model.valueobject.RoleEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Entity
@SqlResultSetMapping(
        name = "TopTournamentPlayerMapping",
        classes = @ConstructorResult(
                targetClass = TopTournamentPlayer.class,
                columns = {
                        @ColumnResult(name = "id", type = UUID.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "chess_grade", type = String.class),
                        @ColumnResult(name = "successScore", type = Integer.class)
                }
        )
)
@Table(name = "player")
public class Player extends BaseEntity implements Serializable {
    private String name;
    private String email;
    private int rating;
    private int gamesPlayed;
    private int gamesWon;
    private String chessGrade; // Гроссмейстер/1 юношеский разряд
    private String password;
    private RoleEnum role;

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

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    public RoleEnum getRole() {
        return role;
    }

    public Player() {}

    public Player(String name, String email, String password, RoleEnum role, String chessGrade) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.rating = 1;
        this.chessGrade = chessGrade;
    }

    public Player(String name, String email, int rating, int gamesPlayed, int gamesWon, String chessGrade) {
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.chessGrade = chessGrade;
    }

    public Player(String name, String email, int rating, int gamesPlayed, int gamesWon, String chessGrade, String password, RoleEnum role) {
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.chessGrade = chessGrade;
        this.password = password;
        this.role = role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
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
