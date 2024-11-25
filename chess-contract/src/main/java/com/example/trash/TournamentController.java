package com.example.trash;

import com.example.input.TournamentCreateInputModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;

@RequestMapping("/tournaments")
public interface TournamentController {

    /**
     * Отображает список турниров с фильтрацией и пагинацией
     */
    @GetMapping
    String listTournaments(
            @ModelAttribute("filter") TournamentFilterInputModel filter,
            Model model
    );

    /**
     * Отображает форму для создания нового турнира
     */
    @GetMapping("/create")
    String showCreateForm(Model model);

    /**
     * Обрабатывает создание нового турнира
     */
    @PostMapping("/create")
    String createTournament(
            @ModelAttribute("tournament") @Valid TournamentCreateInputModel tournament,
            BindingResult bindingResult,
            Model model
    );

    /**
     * Отображает детали турнира
     */
    @GetMapping("/{id}")
    String viewTournament(
            @PathVariable("id") UUID id,
            Model model
    );
}
