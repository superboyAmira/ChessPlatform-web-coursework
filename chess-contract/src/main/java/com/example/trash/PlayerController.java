package com.example.trash;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping("/players")
public interface PlayerController {

    /**
     * Отображает историю партий конкретного игрока.
     */
    @GetMapping("/{id}/history")
    String showPlayerGameHistory(
            @PathVariable("id") UUID playerId,
            Model model
    );
}