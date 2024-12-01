package ru.chessplatform.ui.controller;

import com.example.controllers.PageFirstController;
import com.example.viewmodel.TopTournamentPlayerViewModel;
import com.example.viewmodel.TournamentViewModel;
import com.example.viewmodel.BaseViewModel;
import com.example.viewmodel.CustomPage1ViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;

import java.util.List;

@RequestMapping("/page_first")
public class FirstPageController implements PageFirstController {
    TournamentDomainService tournamentDomainService;
    PlayerDomainService playerDomainService;

    public FirstPageController(TournamentDomainService tournamentDomainService, PlayerDomainService playerDomainService) {
        this.tournamentDomainService = tournamentDomainService;
        this.playerDomainService = playerDomainService;
    }


/*    @Override
    @GetMapping
    public CustomPage1ViewModel showFirstPage(Model model) {
        List<TournamentViewModel> topPrizeTournaments = tournamentDomainService.getUpcomingTournaments(0)
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
        List<TopTournamentPlayerViewModel> topPlayers = this.playerDomainService.getTopTournamentPlayers()
                .stream()
                .map(player -> new TopTournamentPlayerViewModel(
                        player.getChessGrade(),
                        player.getName(),
                        player.getSuccessScore()
                ))
                .toList();
        return new CustomPage1ViewModel(
                createBaseViewModel("Page1"),
                topPrizeTournaments,
                topPlayers
        );
    }*/

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title,
                ""
        );
    }
}
