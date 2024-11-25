package com.example.trash;


import com.example.input.GameCreateInputModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RequestMapping("/games")
public interface GameController {

    /**
     * Отображает список игр.
     */
    @GetMapping
    String listGames(Model model);

    /**
     * Отображает форму для создания новой игры.
     */
    @GetMapping("/create")
    String showCreateForm(Model model);

    /**
     * Обрабатывает создание новой игры.
     */
    @PostMapping("/create")
    String createGame(
            @ModelAttribute("game") @Valid GameCreateInputModel game,
            BindingResult bindingResult,
            Model model
    );

    /**
     * Отображает детали игры.
     */
    @GetMapping("/{id}")
    String viewGame(
            @PathVariable("id") UUID id,
            Model model
    );

    // Дополнительные методы
}