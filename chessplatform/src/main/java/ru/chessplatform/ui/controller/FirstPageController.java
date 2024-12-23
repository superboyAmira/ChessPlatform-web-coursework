package ru.chessplatform.ui.controller;

import com.example.controllers.PageFirstController;
import com.example.viewmodel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.chessplatform.application.service.CustomService;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;

import java.util.List;

@Controller
public class FirstPageController implements PageFirstController {
    CustomService customService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);


    public FirstPageController(CustomService customService) {
        this.customService = customService;
    }

    @Override
    @GetMapping
    public String showFirstPage(Model model) {
        LOG.info("Page 1 viewed: {}", model);
        List<TournamentViewModel> topPrizeTournaments = customService.getHotTournaments()
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

        List<TopTournamentPlayerViewModel> topPlayers = this.customService.getTopTournamentPlayers()
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
