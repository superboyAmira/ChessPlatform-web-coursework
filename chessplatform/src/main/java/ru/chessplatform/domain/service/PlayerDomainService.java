package ru.chessplatform.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import ru.chessplatform.application.dto.TopTournamentPlayer;
import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.repository.PlayerRepository;

@Service
public class PlayerDomainService {
    private final PlayerRepository playerRepository;

    public PlayerDomainService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getTopPlayersByRating() {
        return playerRepository.getTopRatingPlayers();
    }

    public Long getActivePlayersCount() {
        return playerRepository.getAmountOfPlayers();
    }

    public List<TopTournamentPlayer> getTopTournamentPlayers() {
        return playerRepository.findTopTournamentPlayers(10);
    }

    public Optional<Player> getPlayerById(UUID id) {
        return playerRepository.findById(id);
    }

    public List<Player> findAll(int limit, int offset) {
        return playerRepository.findAll(limit, offset);
    }

    public void save(Player player) {
        playerRepository.save(player);
    }

    public void update(UUID id, Player updatedEntity) {
        playerRepository.save(updatedEntity);
    }

    public Optional<Player> findById(UUID id) {
        return playerRepository.findById(id);
    }

    public List<Player> findByNameContaining(String name) {
        return playerRepository.findByName(name);
    }

    public Optional<Player> getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email);
    }
}
