package com.example.controllers;

import com.example.input.GameInputModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/admin/games")
public interface GameController {
    @GetMapping
    String listGames(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model);
    @GetMapping("/create")
    String showCreateForm(Model model);

    @PostMapping("/create")
    String createGame(@ModelAttribute @Validated GameInputModel input, BindingResult result, Model model);

    @GetMapping("/edit/{id}")
    String showEditForm(@PathVariable UUID id, Model model);

    @PostMapping("/edit/{id}")
    String editGame(@PathVariable UUID id, @ModelAttribute @Validated GameInputModel input, BindingResult result, Model model);
}

