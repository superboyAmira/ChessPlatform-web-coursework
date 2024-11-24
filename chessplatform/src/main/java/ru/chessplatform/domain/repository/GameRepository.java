package ru.chessplatform.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ru.chessplatform.domain.model.entity.Game;

public interface GameRepository {
    void save(Game game);
    List<Game> findAll();
    Optional<Game> findById(UUID id);
    List<Game> findByPlayerId(UUID playerId);
}