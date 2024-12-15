package ru.chessplatform.ui.controller;

import com.example.controllers.MainController;
import com.example.input.PlayerInputModel;
import com.example.viewmodel.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.service.GameDomainService;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;
import ru.chessplatform.domain.service.mapper.TournamentMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        Optional<Player> id = playerService.getPlayerByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (id.isEmpty()) {
            model.addAttribute("userId", "null");
        } else {
            model.addAttribute("userId", id.get().getId());
        }


        List<TournamentViewModel> upcomingTournaments = tournamentService.getUpcomingTournaments()
                .stream()
                .map(tournament -> TournamentMapper.toViewModel(tournament))
                .toList();

        List<PlayerViewModel> topPlayers = playerService.getTopPlayersByRating()
                .stream()
                .map(player -> new PlayerViewModel(
                        player.getId(),
                        player.getName(),
                        player.getRating(),
                        player.getChessGrade()
                )).toList();

        List<GameViewModel> recentGrandmasterGames = gameService.getRecentCompletedGamesByGM()
                .stream()
                .map(game -> new GameViewModel(
                        game.getId(),
                        new PlayerViewModel(game.getPlayer1().getId(), game.getPlayer1().getName(), game.getPlayer1().getRating(), game.getPlayer1().getChessGrade()),
                        new PlayerViewModel(game.getPlayer2().getId(), game.getPlayer2().getName(), game.getPlayer2().getRating(), game.getPlayer2().getChessGrade()),
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
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");

        return new BaseViewModel(title, currentUsername, currentUserRole);
    }
}
