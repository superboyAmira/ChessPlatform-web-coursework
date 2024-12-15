package com.example.controllers;

import com.example.input.PlayerInputModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/players")
public interface PlayerController extends BaseController {

    @GetMapping
    public String listPlayers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model);
    @GetMapping("/admin/create")
    String showCreateForm(Model model);

    @PostMapping("/admin/create")
    String createPlayer(@ModelAttribute @Validated PlayerInputModel input, BindingResult result, Model model);

    @GetMapping("/admin/edit/{id}")
    String showEditForm(@PathVariable UUID id, Model model);

    @PostMapping("/admin/edit/{id}")
    public String editPlayer(@PathVariable UUID id, @ModelAttribute @Validated PlayerInputModel input, BindingResult result, Model model);

    @GetMapping("/search")
    String searchPlayersByName(@RequestParam String name, Model model);

    @GetMapping("/{id}")
    String getPlayer(@PathVariable UUID id, Model model);
}
