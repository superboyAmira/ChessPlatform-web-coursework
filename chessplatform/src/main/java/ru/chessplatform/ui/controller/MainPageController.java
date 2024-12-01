package ru.chessplatform.ui.controller;

import com.example.controllers.MainController;
import com.example.viewmodel.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.chessplatform.domain.service.GameDomainService;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;

import java.util.List;

@Controller
public class MainPageController implements MainController {
    private final GameDomainService gameService;
    private final PlayerDomainService playerService;
    private final TournamentDomainService tournamentService;

    public MainPageController(GameDomainService gameService, PlayerDomainService playerService, TournamentDomainService tournamentService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.tournamentService = tournamentService;
    }

    @Override
    public String showHomePage(Model model) {
        BaseViewModel base = createBaseViewModel("Main Page");

        // Добавляем объект base в модель
        model.addAttribute("base", base);
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
                        tournament.getPrizePool(),
                        tournament.getStatus(),
                        null
                ))
                .toList();

        List<TopTournamentPlayerViewModel> topPlayers = playerService.getTopTournamentPlayers()
                .stream()
                .map(player -> new TopTournamentPlayerViewModel(
                        player.getName(),
                        player.getChessGrade(),
                        player.getSuccessScore()
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

        model.addAttribute("activePlayersCount", activePlayersCount);
        model.addAttribute("totalGamesPlayed", totalGamesPlayed);
        model.addAttribute("upcomingTournaments", upcomingTournaments);
        model.addAttribute("topPlayers", topPlayers);
        model.addAttribute("recentGrandmasterGames", recentGrandmasterGames);

        return "index";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        return new BaseViewModel(
                title,
                currentUser
        );
    }
}
