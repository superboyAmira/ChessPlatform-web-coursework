package com.example.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public class PlayerInputModel {
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Некорректный формат электронной почты")
    private String email;
    @Min(value = 1, message = "Rating is minimum 1")
    private int rating;
    @PositiveOrZero(message = "Рейтинг не может быть отрицательным")
    private int gamesPlayed;
    @PositiveOrZero(message = "Рейтинг не может быть отрицательным")
    private int gamesWon;
    @NotBlank(message = "Разряд шахматиста обязателен")
    private String chessGrade;

    public PlayerInputModel() {
    }

    public PlayerInputModel(String chessGrade, int gamesWon, int gamesPlayed, int rating, String email, String name, UUID id) {
        this.chessGrade = chessGrade;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
        this.rating = rating;
        this.email = email;
        this.name = name;
        this.id = id;
    }


    public String getChessGrade() {
        return chessGrade;
    }

    public void setChessGrade(String chessGrade) {
        this.chessGrade = chessGrade;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }
}
