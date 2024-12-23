package ru.chessplatform.ui.controller;

import com.example.controllers.TournamentController;
import com.example.input.TournamentEntryInputModel;
import com.example.input.TournamentInputModel;
import com.example.viewmodel.BaseViewModel;
import com.example.viewmodel.TournamentViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.aggregate.TournamentEntry;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.model.valueobject.RoleEnum;
import ru.chessplatform.domain.service.AuthService;
import ru.chessplatform.domain.service.PlayerDomainService;
import ru.chessplatform.domain.service.TournamentDomainService;
import ru.chessplatform.domain.service.mapper.TournamentMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class TournamentControllerImpl implements TournamentController {
    private static final Logger LOG = LogManager.getLogger(TournamentControllerImpl.class);

    private final TournamentDomainService tournamentService;
    private final PlayerDomainService playerDomainService;
    private final AuthService authService;

    public TournamentControllerImpl(TournamentDomainService tournamentService, PlayerDomainService playerDomainService, AuthService authService) {
        this.tournamentService = tournamentService;
        this.playerDomainService = playerDomainService;
        this.authService = authService;
    }

    @Override
    public String listTournaments(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        LOG.info("Listing tournaments, page={}, size={}", page, size);

        BaseViewModel baseViewModel = createBaseViewModel("Tournament List");
        List<TournamentViewModel> tournaments = tournamentService.findAll(size, page);
        long totalTournaments = tournamentService.count();
        int totalPages = (int) Math.ceil((double) totalTournaments / size);

        LOG.debug("Found {} tournaments, total pages={}", totalTournaments, totalPages);

        model.addAttribute("base", baseViewModel);
        model.addAttribute("tournaments", tournaments);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "tournaments";
    }

    @Override
    public String showCreateForm(Model model) {
        LOG.info("Showing create tournament form");
        BaseViewModel baseViewModel = createBaseViewModel("Create Tournament");
        model.addAttribute("base", baseViewModel);
        model.addAttribute("tournament", new TournamentInputModel());
        return "admin/tournamentcreate";
    }

    @Override
    public String createTournament(@ModelAttribute @Validated TournamentInputModel input, BindingResult result, Model model) {
        LOG.info("Creating tournament with input: {}", input);

        if (result.hasErrors()) {
            LOG.warn("Validation errors occurred: {}", result.getAllErrors());
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
        LOG.info("Tournament created successfully: {}", tournament);

        return "redirect:/tournaments";
    }

    @Override
    public String showEditForm(@PathVariable UUID id, Model model) {
        LOG.info("Showing edit form for tournament ID: {}", id);

        BaseViewModel baseViewModel = createBaseViewModel("Edit Tournament");
        Optional<Tournament> tournament = tournamentService.findById(id);

        if (tournament.isEmpty()) {
            LOG.warn("Tournament not found for ID: {}", id);
            model.addAttribute("error", "Tournament not found");
            return "error";
        }

        TournamentInputModel inputModel = tournament.map(value -> new TournamentInputModel(
                value.getId(),
                value.getParticipantCount(),
                value.getStartDate(),
                value.getName(),
                value.getTournamentType(),
                value.getPrizePool()
        )).orElse(null);

        model.addAttribute("base", baseViewModel);
        model.addAttribute("tournament", inputModel);

        LOG.debug("Edit form data prepared for tournament: {}", inputModel);
        return "admin/tournamentedit";
    }

    @Override
    public String editTournament(@PathVariable UUID id, @ModelAttribute @Validated TournamentInputModel input, BindingResult result, Model model) {
        LOG.info("Editing tournament ID: {}, input: {}", id, input);

        if (result.hasErrors()) {
            LOG.warn("Validation errors occurred: {}", result.getAllErrors());
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

        LOG.info("Tournament edited successfully: {}", tournament);
        return "redirect:/tournaments";
    }

    @Override
    public String showTournament(UUID tournamentId, Model model) {
        LOG.info("Showing details for tournament ID: {}", tournamentId);

        Optional<Tournament> tournamentOptional = tournamentService.findById(tournamentId);

        if (tournamentOptional.isEmpty()) {
            LOG.warn("Tournament not found for ID: {}", tournamentId);
            model.addAttribute("error", "Tournament not found");
            return "error";
        }

        Tournament tournament = tournamentOptional.get();
        TournamentViewModel viewModel = TournamentMapper.toViewModel(tournament);

        BaseViewModel baseViewModel = createBaseViewModel("Tournament Details");
        model.addAttribute("base", baseViewModel);
        model.addAttribute("tournament", viewModel);
        model.addAttribute("newEntry", new TournamentEntryInputModel());

        LOG.debug("Tournament details loaded: {}", viewModel);
        return "tournament";
    }

    @Override
    public String addTournamentEntry(UUID tournamentId, @Validated @ModelAttribute TournamentEntryInputModel input, BindingResult result, Model model) {
        LOG.info("Adding entry to tournament ID: {}, input: {}", tournamentId, input);

        if (result.hasErrors()) {
            LOG.warn("Validation errors occurred: {}", result.getAllErrors());
            return showTournament(tournamentId, model);
        }

        Optional<Tournament> tournamentOptional = tournamentService.findById(tournamentId);
        if (tournamentOptional.isEmpty()) {
            LOG.warn("Tournament not found for ID: {}", tournamentId);
            model.addAttribute("error", "Tournament not found");
            return "error";
        }

        Optional<Player> playerOptional = playerDomainService.findById(input.getPlayerId());
        if (playerOptional.isEmpty()) {
            LOG.warn("Player not found for ID: {}", input.getPlayerId());
            result.rejectValue("playerId", "error.newEntry", "Player not found");
            return showTournament(tournamentId, model);
        }

        Tournament tournament = tournamentOptional.get();
        Player player = playerOptional.get();

        TournamentEntry entry = new TournamentEntry(tournament, player, input.getPoints(), input.getGamesPlayed());
        tournament.addEntry(entry);
        tournamentService.save(tournament);

        LOG.info("Entry added successfully for player ID: {}", player.getId());
        return "redirect:/tournaments/" + tournamentId;
    }

    @Override
    public String joinTournament(@PathVariable UUID tournamentId, Model model) {
        LOG.info("Player attempting to join tournament ID: {}", tournamentId);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Player> playerOptional = authService.getUserByEmail(currentUsername);

        if (playerOptional.isEmpty()) {
            LOG.warn("Player not found for username: {}", currentUsername);
            model.addAttribute("error", "Player not found");
            return "error";
        }

        Player player = playerOptional.get();

        if (!player.getRole().equals(RoleEnum.PLAYER)) {
            LOG.warn("Unauthorized role for joining tournament: {}", player.getRole());
            model.addAttribute("error", "Only players can join tournaments");
            return "error";
        }

        Optional<Tournament> tournamentOptional = tournamentService.findById(tournamentId);
        if (tournamentOptional.isEmpty()) {
            LOG.warn("Tournament not found for ID: {}", tournamentId);
            model.addAttribute("error", "Tournament not found");
            return "error";
        }

        Tournament tournament = tournamentOptional.get();
        boolean alreadyJoined = tournament.getEntries().stream()
                .anyMatch(entry -> entry.getPlayer().getId().equals(player.getId()));

        if (alreadyJoined) {
            LOG.warn("Player already joined tournament ID: {}", tournamentId);
            model.addAttribute("error", "Player already joined this tournament");
            return "redirect:/tournaments/" + tournamentId;
        }

        TournamentEntry entry = new TournamentEntry(tournament, player, 0, 0);
        tournament.addEntry(entry);
        tournamentService.save(tournament);

        LOG.info("Player joined tournament successfully: player ID={}, tournament ID={}", player.getId(), tournamentId);
        return "redirect:/tournaments/" + tournamentId;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");

        LOG.debug("Creating BaseViewModel for title={}, username={}, role={}", title, currentUsername, currentUserRole);
        return new BaseViewModel(title, currentUsername, currentUserRole);
    }
}
