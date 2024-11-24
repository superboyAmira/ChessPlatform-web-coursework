package ru.chessplatform.application.dto;

import java.util.List;
import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.entity.Game;
import ru.chessplatform.domain.model.entity.Player;

public class MainPageStatisticsDto {
    private final long activePlayerCount;
    private final long currentGameCount;
    private final List<Tournament> upcomingTournaments;
    private final List<Player> topPlayers;
    private final List<Game> recentCompletedGames;
    private final List<Tournament> activeTournaments;

    public MainPageStatisticsDto(long activePlayerCount,
                                 long currentGameCount,
                                 List<Tournament> upcomingTournaments,
                                 List<Player> topPlayers,
                                 List<Game> recentCompletedGames,
                                 List<Tournament> activeTournaments) {
        this.activePlayerCount = activePlayerCount;
        this.currentGameCount = currentGameCount;
        this.upcomingTournaments = upcomingTournaments;
        this.topPlayers = topPlayers;
        this.recentCompletedGames = recentCompletedGames;
        this.activeTournaments = activeTournaments;
    }

    public long getActivePlayerCount() {
        return activePlayerCount;
    }

    public long getCurrentGameCount() {
        return currentGameCount;
    }

    public List<Tournament> getUpcomingTournaments() {
        return upcomingTournaments;
    }

    public List<Player> getTopPlayers() {
        return topPlayers;
    }

    public List<Game> getRecentCompletedGames() {
        return recentCompletedGames;
    }

    public List<Tournament> getActiveTournaments() {
        return activeTournaments;
    }
}
