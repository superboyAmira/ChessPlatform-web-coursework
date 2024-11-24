package com.example.trash;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class TournamentCreateInputModel {

    @NotBlank(message = "Название турнира обязательно")
    private String name;

    @NotNull(message = "Дата начала обязательна")
    private LocalDateTime startDate;

    @Min(value = 2, message = "Количество участников должно быть не менее 2")
    private int participantCount;

    @NotBlank(message = "Тип турнира обязателен")
    private String tournamentType;

    @PositiveOrZero(message = "Призовой фонд не может быть отрицательным")
    private double prizePool;

    // Геттеры и сеттеры
    // ...
}
