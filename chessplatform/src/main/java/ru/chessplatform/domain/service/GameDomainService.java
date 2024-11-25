package ru.chessplatform.domain.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import ru.chessplatform.domain.model.entity.Game;
import ru.chessplatform.domain.repository.GameRepository;

@Service
public class GameDomainService {
    private final GameRepository gameRepository;

    public GameDomainService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getRecentCompletedGamesByGM() {
        return gameRepository.findGMGames();
    }

    public List<Game> getGamesByPlayerId(UUID playerId) {
        return gameRepository.findByPlayerId(playerId);
    }

    public Long getTotalGamesPlayed() {
        return gameRepository.getCountAllGames();
    }
}
