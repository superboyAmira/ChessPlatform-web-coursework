package ru.chessplatform.ui.controller;

import com.example.controllers.PlayerController;
import com.example.input.PlayerInputModel;
import com.example.viewmodel.BaseViewModel;
import com.example.viewmodel.PlayerViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.service.PlayerDomainService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PlayerControllerImpl implements PlayerController {
    private final PlayerDomainService playerDomainService;

    public PlayerControllerImpl(PlayerDomainService playerDomainService) {
        this.playerDomainService = playerDomainService;
    }

    @Override
    public String listPlayers(int page, int size, Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Players List");

        List<PlayerViewModel> playerViewModelList = playerDomainService.findAll(size, page);

        long totalPlayers = playerDomainService.count();

        int totalPages = (int) Math.ceil((double) totalPlayers / size);

        model.addAttribute("base", baseViewModel);
        model.addAttribute("players", playerViewModelList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "players";
    }

    @Override
    public String showCreateForm(Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Create Player");
        model.addAttribute("base", baseViewModel);
        model.addAttribute("player", new PlayerInputModel());
        return "admin/playercreate";
    }

    @Override
    public String createPlayer(PlayerInputModel input, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return this.showCreateForm(model);
        }
        Player player = new Player(
                input.getName(),
                input.getEmail(),
                input.getRating(),
                input.getGamesWon(),
                input.getGamesPlayed(),
                input.getChessGrade()
        );
        playerDomainService.save(player);
        return "redirect:/players";
    }

    @Override
    public String showEditForm(UUID id, Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Edit Player");
        Optional<Player> player = playerDomainService.findById(id);
        PlayerInputModel inputModel = player.map(value -> new PlayerInputModel(
                value.getChessGrade(),
                value.getGamesWon(),
                value.getGamesPlayed(),
                value.getRating(),
                value.getEmail(),
                value.getName(),
                value.getId()
        )).orElse(null);

        model.addAttribute("base", baseViewModel);
        model.addAttribute("player", inputModel);

        return "admin/playeredit";
    }

    @Override
    public String editPlayer(UUID id, PlayerInputModel input, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return this.showEditForm(id, model);
        }
        Player player = new Player(
                input.getEmail(),
                input.getName(),
                input.getRating(),
                input.getGamesWon(),
                input.getGamesPlayed(),
                input.getChessGrade()
        );

        player.setId(input.getId());
        playerDomainService.save(player);
        return "redirect:/players";
    }

    @Override
    public String searchPlayersByName(String name, Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Search Results");
        List<PlayerViewModel> playerViewModelList = playerDomainService.findByNameContaining(name)
                .stream()
                .map(value -> new PlayerViewModel(
                        value.getId(),
                        value.getName(),
                        value.getRating(),
                        value.getChessGrade()
                )).toList();

        model.addAttribute("base", baseViewModel);
        model.addAttribute("players", playerViewModelList);
        model.addAttribute("searchQuery", name);

        return "players";
    }

    @Override
    public String getPlayer(UUID id, Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Search Results");
        Optional<Player> player = playerDomainService.findById(id);
        if (player.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player not found");
        }

        model.addAttribute("base", baseViewModel);
        model.addAttribute("player", player.get());

        return "player";
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
