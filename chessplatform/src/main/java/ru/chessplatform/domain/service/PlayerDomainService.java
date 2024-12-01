package ru.chessplatform.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.chessplatform.application.dto.TopTournamentPlayer;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.repository.PlayerRepository;

@Service
public class PlayerDomainService {
    private final PlayerRepository playerRepository;

    public PlayerDomainService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getTopPlayersByRating(int limit) {
        return playerRepository.findAll(0, 20)
                .stream()
                .sorted((p1, p2) -> Integer.compare(p2.getRating(), p1.getRating())) // Сортировка по рейтингу
                .limit(limit) // Ограничиваем количество игроков
                .toList();
    }

    public Long getActivePlayersCount() {
        return playerRepository.getAmountOfPlayers();
    }

    public List<TopTournamentPlayer> getTopTournamentPlayers() {
        return playerRepository.findTopTournamentPlayers(10)
                .stream()
                .map(record -> new TopTournamentPlayer(
                        (String) record[0],  // name
                        (String) record[1],  // chessGrade
                        ((Number) record[2]).longValue() // successScore
                ))
                .toList();
    }
}
