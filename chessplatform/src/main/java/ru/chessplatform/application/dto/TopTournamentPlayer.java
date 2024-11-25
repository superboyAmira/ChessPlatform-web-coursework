package ru.chessplatform.application.dto;

public class TopTournamentPlayer {
    private final String ChessGrade;
    private final String name;
    private final long successScore;

    public TopTournamentPlayer(String ChessGrade, String name, long successScore) {
        this.ChessGrade = ChessGrade;
        this.name = name;
        this.successScore = successScore;
    }

    public String getChessGrade() {
        return ChessGrade;
    }

    public String getName() {
        return name;
    }

    public long getSuccessScore() {
        return successScore;
    }
}