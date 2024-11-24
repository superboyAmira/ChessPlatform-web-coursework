package ru.chessplatform.domain.service;

import java.util.List;

import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.repository.PlayerRepository;

public class PlayerDomainService {
    private final PlayerRepository playerRepository;

    public PlayerDomainService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getTopPlayersByRating(int limit) {
        return playerRepository.findAll();
    }

    // public long getActivePlayerCount() {
    //     // return playerRepository.countActivePlayers();
    // }
}
