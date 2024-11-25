package ru.chessplatform.ui.controller;


import com.example.controllers.MainController;
import com.example.viewmodel.GameViewModel;
import com.example.viewmodel.PlayerViewModel;
import com.example.viewmodel.TournamentViewModel;
import com.example.viewmodel.BaseViewModel;
import com.example.viewmodel.MainPageViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chessplatform.domain.service.GameDomainService;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;

import java.util.List;

@RequestMapping("/")
public class MainPageController implements MainController {
    GameDomainService gameService;
    PlayerDomainService playerService;
    TournamentDomainService tournamentService;

    public MainPageController(GameDomainService gameService, PlayerDomainService playerService, TournamentDomainService tournamentService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.tournamentService = tournamentService;
    }

    @GetMapping
    @Override
    public MainPageViewModel showHomePage(Model model) {
        long activePlayersCount = playerService.getActivePlayersCount();

        long totalGamesPlayed = gameService.getTotalGamesPlayed();

        List<TournamentViewModel> upcomingTournaments = tournamentService.getUpcomingTournaments(0)
                .stream()
                .map(tournament -> new TournamentViewModel(
                        tournament.getId(),
                        tournament.getName(),
                        tournament.getStartDate(),
                        tournament.getParticipantCount(),
                        tournament.getTournamentType(),
                        tournament.getPrizePool()
                ))
                .toList();

        List<PlayerViewModel> topPlayers = playerService.getTopPlayersByRating(5)
                .stream()
                .map(player -> new PlayerViewModel(
                        player.getName(),
                        player.getRating(),
                        player.getChessGrade()
                ))
                .toList();

        List<GameViewModel> recentGrandmasterGames = gameService.getRecentCompletedGamesByGM()
                .stream()
                .map(game -> new GameViewModel(
                        game.getId(),
                        new PlayerViewModel(game.getPlayer1().getName(), game.getPlayer1().getRating(), game.getPlayer1().getChessGrade()),
                        new PlayerViewModel(game.getPlayer2().getName(), game.getPlayer2().getRating(), game.getPlayer2().getChessGrade()),
                        game.getResult(),
                        game.getGameType(),
                        game.getStartTime(),
                        game.getDuration()
                ))
                .toList();

        BaseViewModel base = new BaseViewModel(
                "Main Page",
                "currentUserName"
        );

        return new MainPageViewModel(
                base,
                activePlayersCount,
                totalGamesPlayed,
                upcomingTournaments,
                topPlayers,
                recentGrandmasterGames
        );
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title,
                ""
        );
    }
}