package com.example.controllers;

import com.example.input.TournamentEntryInputModel;
import com.example.input.TournamentInputModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/tournaments")
public interface TournamentController extends BaseController {

    @GetMapping
    public String listTournaments(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model);

    @GetMapping("/admin/create")
    String showCreateForm(Model model);

    @PostMapping("/admin/create")
    String createTournament(@ModelAttribute @Validated TournamentInputModel input, BindingResult result, Model model);

    @GetMapping("/admin/edit/{id}")
    String showEditForm(@PathVariable UUID id, Model model);

    @PostMapping("/admin/edit/{id}")
    public String editTournament(@PathVariable UUID id, @ModelAttribute @Validated TournamentInputModel input, BindingResult result, Model model);

    @GetMapping("/{tournamentId}")
    String showTournament(@PathVariable UUID tournamentId, Model model);

    @PostMapping("/{tournamentId}/join")
    String joinTournament(@PathVariable UUID tournamentId, Model model);

        @PostMapping("/adminф/{tournamentId}/add-entry")
    String addTournamentEntry(@PathVariable UUID tournamentId,
                              @ModelAttribute @Validated TournamentEntryInputModel input,
                              BindingResult result,
                              Model model);
}