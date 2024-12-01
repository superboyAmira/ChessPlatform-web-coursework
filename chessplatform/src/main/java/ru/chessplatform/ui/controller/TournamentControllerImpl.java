package ru.chessplatform.ui.controller;

import com.example.controllers.TournamentController;
import com.example.input.TournamentInputModel;
import com.example.viewmodel.BaseViewModel;
import com.example.viewmodel.TournamentViewModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.service.TournamentDomainService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class TournamentControllerImpl implements TournamentController {
    private final TournamentDomainService tournamentService;

    public TournamentControllerImpl(TournamentDomainService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Override
    public String listTournaments(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Tournament List");

        // Получение турниров с пагинацией
        List<TournamentViewModel> tournaments = tournamentService.findAll(size, page)
                .stream()
                .map(t -> new TournamentViewModel(
                        t.getId(),
                        t.getName(),
                        t.getStartDate(),
                        t.getParticipantCount(),
                        t.getTournamentType(),
                        t.getPrizePool(),
                        t.getStatus(),
                        null)
                )
                .toList();

        // Общее количество турниров (для расчета количества страниц)
        long totalTournaments = tournamentService.count();


        // Количество страниц
        int totalPages = (int) Math.ceil((double) totalTournaments / size);

        model.addAttribute("base", baseViewModel);
        model.addAttribute("tournaments", tournaments);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin/tournaments";
    }


    @Override
    public String showCreateForm(Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Create Tournament");
        model.addAttribute("base", baseViewModel);
        model.addAttribute("tournament", new TournamentInputModel());
        return "admin/tournamentcreate";
    }

    @Override
    public String createTournament(@ModelAttribute @Validated TournamentInputModel input, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return this.showCreateForm(model);
        }
        Tournament tournament = new Tournament(
                input.getName(),
                input.getStartDate(),
                input.getParticipantCount(),
                input.getTournamentType(),
                input.getPrizePool()
        );
        tournamentService.save(tournament);
        return "redirect:/admin/tournaments";
    }

    @Override
    public String showEditForm(@PathVariable UUID id, Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Edit Tournament");
        Optional<Tournament> tournament = tournamentService.findById(id);
        TournamentInputModel inputModel = tournament.map(value -> new TournamentInputModel(
                value.getId(),
                value.getParticipantCount(),
                value.getStartDate(),
                value.getName(),
                value.getTournamentType(),
                value.getPrizePool()
        )).orElse(null);

        ; // Установите ID



        model.addAttribute("base", baseViewModel);
        model.addAttribute("tournament", inputModel);

        return "admin/tournamentedit";
    }

    @Override
    public String editTournament(@PathVariable UUID id, @ModelAttribute @Validated TournamentInputModel input, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return this.showEditForm(id, model);
        }
        Tournament tournament = new Tournament(
                input.getName(),
                input.getStartDate(),
                input.getParticipantCount(),
                input.getTournamentType(),
                input.getPrizePool()
        );
        tournament.setId(id);
        tournamentService.save(tournament);
        return "redirect:/admin/tournaments";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return new BaseViewModel(title, currentUser);
    }
}
