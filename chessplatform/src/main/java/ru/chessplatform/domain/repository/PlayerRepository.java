package ru.chessplatform.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ru.chessplatform.application.dto.TopTournamentPlayer;
import ru.chessplatform.domain.model.entity.Player;

public interface PlayerRepository {
    void save(Player player);
    List<Player> findAll(int limit, int offset);;
    Optional<Player> findById(UUID id);
    List<Player> findByName(String name);
    Long getAmountOfPlayers();

    List<Player> getTopRatingPlayers();
    List<TopTournamentPlayer> findTopTournamentPlayers(int limit);

    Optional<Player> findByEmail(String email);
    boolean existsByEmail(String email);
}
