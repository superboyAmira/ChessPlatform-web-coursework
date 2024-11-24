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

    public List<Game> getRecentCompletedGames(int limit) {
        // Пример бизнес-логики: получение завершенных игр с сортировкой по дате
        return gameRepository.findAll().stream()
                .filter(game -> game.getResult() != null)
                .sorted((g1, g2) -> g2.getStartTime().compareTo(g1.getStartTime()))
                .limit(limit)
                .toList();
    }

    public List<Game> getGamesByPlayerId(UUID playerId) {
        return gameRepository.findByPlayerId(playerId);
    }
}
