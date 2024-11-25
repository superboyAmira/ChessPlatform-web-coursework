package com.example.viewmodel;

public class TopTournamentPlayerViewModel {
    String name;
    String chessGrade;
    Long CoeffSum;

    public TopTournamentPlayerViewModel(String ChessGrade, String name, long successScore) {
        this.name = name;
        this.chessGrade = ChessGrade;
        this.CoeffSum = successScore;
    }
}
