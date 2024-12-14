package ru.chessplatform.ui.controller;

import com.example.controllers.PageFirstController;
import com.example.viewmodel.*;
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
        List<TournamentViewModel> topPrizeTournaments = tournamentDomainService.getUpcomingTournaments(0)
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
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return new BaseViewModel(title, currentUser);
    }
}
