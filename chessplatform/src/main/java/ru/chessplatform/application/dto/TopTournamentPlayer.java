package ru.chessplatform.application.dto;

public class TopTournamentPlayer {
    private final String name;
    private final String chessGrade;
    private final long successScore;

    public TopTournamentPlayer(String name, String chessGrade, long successScore) {
        this.name = name;
        this.chessGrade = chessGrade;
        this.successScore = successScore;
    }

    public String getName() {
        return name;
    }

    public String getChessGrade() {
        return chessGrade;
    }

    public long getSuccessScore() {
        return successScore;
    }
}
