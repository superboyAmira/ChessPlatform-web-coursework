package ru.chessplatform.ui.controller;

import com.example.controllers.PageFirstController;
import com.example.viewmodel.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;

import java.util.List;

@Controller
public class FirstPageController implements PageFirstController {
    TournamentDomainService tournamentDomainService;
    PlayerDomainService playerDomainService;

    public FirstPageController(TournamentDomainService tournamentDomainService, PlayerDomainService playerDomainService) {
        this.tournamentDomainService = tournamentDomainService;
        this.playerDomainService = playerDomainService;
    }

    @Override
    @GetMapping
    public String showFirstPage(Model model) {
        List<TournamentViewModel> topPrizeTournaments = tournamentDomainService.getHotTournaments()
                .stream()
                .map(tournament -> new TournamentViewModel(
                        tournament.getId(),
                        tournament.getName(),
                        tournament.getStartDate(),
                        tournament.getParticipantCount(),
                        tournament.getTournamentType(),
                        tournament.getPrizePool(),
                        tournament.getStatus(),
                        tournament.getEntries().stream().map(entry -> new TournamentEntryViewModel(
                                entry.getPlayer().getId(),
                                entry.getPlayer().getName(),
                                entry.getPoints(),
                                entry.getGamesPlayed()
                        )).toList()
                ))
                .toList();

        List<TopTournamentPlayerViewModel> topPlayers = this.playerDomainService.getTopTournamentPlayers()
                .stream()
                .map(player -> new TopTournamentPlayerViewModel(
                        player.getId(),
                        player.getChessGrade(),
                        player.getName(),
                        player.getSuccessScore()
                ))
                .toList();

        BaseViewModel base = createBaseViewModel("First Page");
        model.addAttribute("topPrizeTournaments", topPrizeTournaments);
        model.addAttribute("topPlayers", topPlayers);
        model.addAttribute("base", base);

        return "page_first";
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
