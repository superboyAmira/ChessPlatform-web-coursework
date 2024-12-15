package ru.chessplatform.application.service;

import org.springframework.stereotype.Service;
import ru.chessplatform.application.dto.TopTournamentPlayer;
import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.entity.Game;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.repository.*;

import java.util.List;

@Service
public class CustomService {
    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;
    private final PlayerRepository playerRepository;
    private final TournamentEntryRepository tournamentEntryRepository;
    private final TournamentRepository tournamentRepository;

    public CustomService(GameRepository gameRepository, MoveRepository moveRepository, PlayerRepository playerRepository, TournamentEntryRepository tournamentEntryRepository, TournamentRepository tournamentRepository) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
        this.playerRepository = playerRepository;
        this.tournamentEntryRepository = tournamentEntryRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public List<Game> getRecentCompletedGamesByGM() {
        return gameRepository.findGMGames();
    }

    public Long getTotalGamesPlayed() {
        return gameRepository.getCountAllGames();
    }

    public List<Player> getTopPlayersByRating() {
        return playerRepository.getTopRatingPlayers();
    }

    public List<TopTournamentPlayer> getTopTournamentPlayers() {
        return playerRepository.findTopTournamentPlayers(10);
    }

    public List<Tournament> getUpcomingTournaments() {
        return tournamentRepository.findUpcomingTourmnametns();
    }

    public List<Tournament> getHotTournaments() {
        return tournamentRepository.findSpecialTournament();
    }

}
