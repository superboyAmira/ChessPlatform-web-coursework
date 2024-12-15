package ru.chessplatform.application.dto;

import java.util.UUID;

public class TopTournamentPlayer {
    private final UUID id;
    private final String name;
    private final String chessGrade;
    private final int successScore;

    public TopTournamentPlayer(UUID id, String name, String chessGrade, int successScore) {
        this.id = id;
        this.name = name;
        this.chessGrade = chessGrade;
        this.successScore = successScore;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getChessGrade() {
        return chessGrade;
    }

    public int getSuccessScore() {
        return successScore;
    }
}
