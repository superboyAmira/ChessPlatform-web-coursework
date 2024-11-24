package ru.chessplatform.application.service;

import ru.chessplatform.application.dto.MainPageStatisticsDto;
import ru.chessplatform.domain.service.GameDomainService;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;

public class MainPageService {
    private final PlayerDomainService playerDomainService;
    private final GameDomainService gameDomainService;
    private final TournamentDomainService tournamentDomainService;

    public MainPageService(PlayerDomainService playerDomainService,
                           GameDomainService gameDomainService,
                           TournamentDomainService tournamentDomainService) {
        this.playerDomainService = playerDomainService;
        this.gameDomainService = gameDomainService;
        this.tournamentDomainService = tournamentDomainService;
    }

    public MainPageStatisticsDto getMainPageStatistics() {
        return new MainPageStatisticsDto(
            playerDomainService.getActivePlayerCount(),
            gameDomainService.getCurrentGameCount(),
            tournamentDomainService.getUpcomingTournaments(5),
            playerDomainService.getTopPlayersByRating(5),
            gameDomainService.getRecentCompletedGames(5),
            tournamentDomainService.getActiveTournaments()
        );
    }
}