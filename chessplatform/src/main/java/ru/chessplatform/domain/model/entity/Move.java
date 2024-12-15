package ru.chessplatform.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.chessplatform.domain.model.valueobject.Figure;

@Entity
@Table(name = "move")
public class Move extends BaseEntity implements Serializable {

    private Game game;
    private Player player;
    private int moveNumber;
    private Figure figure;
    private String fromPosition;
    private String toPosition;
    private Figure eliminatedFigure;
    private LocalDateTime moveTime;
    private long durationMove;

    public Move() {}

    public Move(Game game, Player player, int moveNumber, Figure figure, String fromPosition, String toPosition,
                Figure eliminatedFigure, LocalDateTime moveTime, long durationMove) {
        this.game = game;
        this.player = player;
        this.moveNumber = moveNumber;
        this.figure = figure;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.eliminatedFigure = eliminatedFigure;
        this.moveTime = moveTime;
        this.durationMove = durationMove;
    }

    // Геттеры
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonBackReference
    public Game getGame() {
        return game;
    }

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    public Player getPlayer() {
        return player;
    }

    @Column(name = "move_number", nullable = false)
    public int getMoveNumber() {
        return moveNumber;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "figure_name")),
            @AttributeOverride(name = "costPerPoints", column = @Column(name = "figure_cost"))
    })
    public Figure getFigure() {
        return figure;
    }

    @Column(name = "from_position", nullable = false)
    public String getFromPosition() {
        return fromPosition;
    }

    @Column(name = "to_position", nullable = false)
    public String getToPosition() {
        return toPosition;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "eliminated_figure_name")),
            @AttributeOverride(name = "costPerPoints", column = @Column(name = "eliminated_figure_cost"))
    })
    public Figure getEliminatedFigure() {
        return eliminatedFigure;
    }

    @Column(name = "move_time", nullable = false)
    public LocalDateTime getMoveTime() {
        return moveTime;
    }

    @Column(name = "duration_move", nullable = false)
    public long getDurationMove() {
        return durationMove;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public void setFromPosition(String fromPosition) {
        this.fromPosition = fromPosition;
    }

    public void setToPosition(String toPosition) {
        this.toPosition = toPosition;
    }

    public void setEliminatedFigure(Figure eliminatedFigure) {
        this.eliminatedFigure = eliminatedFigure;
    }

    public void setMoveTime(LocalDateTime moveTime) {
        this.moveTime = moveTime;
    }

    public void setDurationMove(long durationMove) {
        this.durationMove = durationMove;
    }

    @Override
    public String toString() {
        return "Move{" +
                "id=" + getId() +
                ", game=" + game.getId() +
                ", player=" + player.getName() +
                ", moveNumber=" + moveNumber +
                ", figure=" + figure +
                ", fromPosition='" + fromPosition + '\'' +
                ", toPosition='" + toPosition + '\'' +
                ", eliminatedFigure=" + eliminatedFigure +
                ", moveTime=" + moveTime +
                ", durationMove=" + durationMove +
                '}';
    }
}
