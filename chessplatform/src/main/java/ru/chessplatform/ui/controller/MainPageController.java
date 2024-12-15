package ru.chessplatform.ui.controller;

import com.example.controllers.MainController;
import com.example.viewmodel.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.chessplatform.application.service.CustomService;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.service.GameDomainService;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;
import ru.chessplatform.domain.service.mapper.TournamentMapper;

import java.util.List;
import java.util.Optional;

@Controller
public class MainPageController implements MainController {
    private final PlayerDomainService playerService;
    private final CustomService customService;

    public MainPageController(CustomService customService, PlayerDomainService playerService) {
        this.playerService = playerService;
        this.customService = customService;
    }

    @Override
    public String showHomePage(Model model) {
        BaseViewModel base = createBaseViewModel("Main Page");

        model.addAttribute("base", base);
        long activePlayersCount = playerService.count();
        long totalGamesPlayed = customService.getTotalGamesPlayed();

        Optional<Player> id = playerService.getPlayerByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (id.isEmpty()) {
            model.addAttribute("userId", "null");
        } else {
            model.addAttribute("userId", id.get().getId());
        }


        List<TournamentViewModel> upcomingTournaments = customService.getUpcomingTournaments()
                .stream()
                .map(tournament -> TournamentMapper.toViewModel(tournament))
                .toList();

        List<PlayerViewModel> topPlayers = customService.getTopPlayersByRating()
                .stream()
                .map(player -> new PlayerViewModel(
                        player.getId(),
                        player.getName(),
                        player.getRating(),
                        player.getChessGrade()
                )).toList();

        List<GameViewModel> recentGrandmasterGames = customService.getRecentCompletedGamesByGM()
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
