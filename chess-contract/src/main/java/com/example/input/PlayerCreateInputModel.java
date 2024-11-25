package com.example.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class PlayerCreateInputModel {

    @NotBlank(message = "Имя игрока обязательно")
    private String name;

    @Email(message = "Некорректный формат электронной почты")
    private String email;

    @PositiveOrZero(message = "Рейтинг не может быть отрицательным")
    private int rating;

    @NotBlank(message = "Разряд шахматиста обязателен")
    private String chessGrade;

    // Геттеры и сеттеры
    // ...
}