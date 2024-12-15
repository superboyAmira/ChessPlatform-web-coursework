package ru.chessplatform.domain.model.entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game extends BaseEntity {
    private Player player1;
    private Player player2;
    private String result; // Победа игрока 1 Победа игрока 2 Ничья
    private String gameType; //Блиц Раптд Пуля
    private LocalDateTime startTime;
    private long duration;
    private List<Move> moves = new ArrayList<>();



    public Game() {}

    public Game(Player player1, Player player2, String result, String gameType, LocalDateTime startTime, long duration) {
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
        this.gameType = gameType;
        this.startTime = startTime;
        this.duration = duration;
    }

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Move> getMoves() {
        return moves;
    }

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    public Player getPlayer1() {
        return player1;
    }

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false)
    public Player getPlayer2() {
        return player2;
    }

    @Column(name = "result")
    public String getResult() {
        return result;
    }

    @Column(name = "game_type", nullable = false)
    public String getGameType() {
        return gameType;
    }

    @Column(name = "start_time", nullable = false)
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Column(name = "duration")
    public long getDuration() {
        return duration;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setDrawResult() {
        this.result = "Ничья";
    }

    public void setWinner(Player player) {
        if (player.equals(player1)) {
            this.result = "Победа игрока 1";
        } else if (player.equals(player2)) {
            this.result = "Победа игрока 2";
        } else {
            throw new IllegalArgumentException("Игрок не является участником этой игры");
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + getId() +
                ", player1=" + player1.getName() +
                ", player2=" + player2.getName() +
                ", result='" + result + '\'' +
                ", gameType='" + gameType + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
