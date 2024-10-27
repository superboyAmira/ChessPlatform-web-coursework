package ru.chessplatform.domain.repository;

import java.util.Optional;
import java.util.UUID;

import ru.chessplatform.domain.model.entity.Player;

public interface PlayerRepository {
    void save(Player player);
    Optional<Player> findById(UUID id);
    Optional<Player> findByEmail(String email);
}
