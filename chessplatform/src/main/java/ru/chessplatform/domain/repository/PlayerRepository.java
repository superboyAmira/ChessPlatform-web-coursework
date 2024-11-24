package ru.chessplatform.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ru.chessplatform.domain.model.entity.Player;

public interface PlayerRepository {
    void save(Player player);
    List<Player> findAll();
    Optional<Player> findById(UUID id);
    Optional<Player> findByEmail(String email);
}
