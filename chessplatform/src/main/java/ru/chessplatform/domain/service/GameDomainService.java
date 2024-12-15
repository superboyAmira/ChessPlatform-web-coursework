package ru.chessplatform.domain.service;

import java.util.List;
import java.util.Optional;
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

    public List<Game> getGamesByPlayerId(UUID playerId, int size, int page) {
        return gameRepository.findByPlayerId(playerId, size, page);
    }

    public Long getTotalGamesPlayed() {
        return gameRepository.getCountAllGames();
    }

    public List<Game> findAll(int limit, int offset) {
        return gameRepository.findAll(limit, offset);
    }

    public Optional<Game> findById(UUID id) {
        return gameRepository.findById(id);
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public long count() {
        return gameRepository.getAmount();
    }
}
