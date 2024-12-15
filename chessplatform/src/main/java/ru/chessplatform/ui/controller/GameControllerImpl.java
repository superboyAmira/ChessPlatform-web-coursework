package ru.chessplatform.ui.controller;

import com.example.controllers.GameController;
import com.example.input.GameInputModel;
import com.example.viewmodel.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ru.chessplatform.domain.model.entity.Game;
import ru.chessplatform.domain.model.entity.Move;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.model.valueobject.Figure;
import ru.chessplatform.domain.service.GameDomainService;
import ru.chessplatform.domain.service.PlayerDomainService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class GameControllerImpl implements GameController {
    private final GameDomainService gameDomainService;
    private final PlayerDomainService playerDomainService;

    public GameControllerImpl(GameDomainService gameDomainService, PlayerDomainService playerDomainService) {
        this.gameDomainService = gameDomainService;
        this.playerDomainService = playerDomainService;
    }

    @Override
    public String listGames(int page, int size, Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Games List");
        List<GameViewModel> games = gameDomainService.findAll(size, page).stream().map(g -> new GameViewModel(
                g.getId(),
                new PlayerViewModel(g.getPlayer1().getId(), g.getPlayer1().getName(), g.getPlayer1().getRating(), g.getPlayer1().getChessGrade()),
                new PlayerViewModel(g.getPlayer2().getId(), g.getPlayer2().getName(), g.getPlayer2().getRating(), g.getPlayer2().getChessGrade()),
                g.getResult(),
                g.getGameType(),
                g.getStartTime(),
                g.getDuration()
        )).toList();

        long totalGameCount = gameDomainService.count();

        int totalPages = (int) Math.ceil((double) totalGameCount / size);

        model.addAttribute("base", baseViewModel);
        model.addAttribute("games", games);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin/allgames";
    }

    @Override
    public String listMyGames(int page, int size, Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("My Games List");
        model.addAttribute("base", baseViewModel);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Player> currentUser = playerDomainService.getPlayerByEmail(currentUsername);

        if (currentUser.isEmpty()) {
            model.addAttribute("error", "Current user not found");
            return "error";
        }

        UUID currentUserId = currentUser.get().getId();

        List<GameViewModel> games = gameDomainService.getGamesByPlayerId(currentUserId, size, page).stream()
                .map(g -> new GameViewModel(
                        g.getId(),
                        new PlayerViewModel(g.getPlayer1().getId(), g.getPlayer1().getName(), g.getPlayer1().getRating(), g.getPlayer1().getChessGrade()),
                        new PlayerViewModel(g.getPlayer2().getId(), g.getPlayer2().getName(), g.getPlayer2().getRating(), g.getPlayer2().getChessGrade()),
                        g.getResult(),
                        g.getGameType(),
                        g.getStartTime(),
                        g.getDuration()
                )).toList();

        model.addAttribute("games", games);

        return "player/games";
    }

    @Override
    public String showCreateForm(Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Create Game");
        model.addAttribute("base", baseViewModel);
        model.addAttribute("game", new GameInputModel());

        return "player/gamecreate";
    }
    @Override
    public String createGame(GameInputModel input, MultipartFile movesFile, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return this.showCreateForm(model);
        }

        // Получение текущего пользователя
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Player> currentUser = playerDomainService.getPlayerByEmail(currentUsername);

        if (currentUser.isEmpty()) {
            model.addAttribute("error", "Current user not found");
            return this.showCreateForm(model);
        }

        Optional<Player> pl2 = playerDomainService.getPlayerById(input.getPlayer2Id());
        if (pl2.isEmpty()) {
            model.addAttribute("error", "Player 2 not found");
            return this.showCreateForm(model);
        }

        // Создание игры
        Game game = new Game(
                currentUser.get(), // Первый игрок — текущий пользователь
                pl2.get(),
                input.getResult(),
                input.getGameType(),
                input.getStartTime(),
                input.getDuration()
        );
        if (!movesFile.isEmpty()) {
            try {
                List<Move> moves = parseMovesFromFile(game, movesFile);
                game.setMoves(moves); // Свяжем ходы с игрой
            } catch (Exception e) {
                model.addAttribute("error", "Error processing moves file: " + e.getMessage());
                return this.showCreateForm(model);
            }
        }

        gameDomainService.save(game);

        return "redirect:/games/" + pl2.get().getId().toString();
    }

    private List<Move> parseMovesFromFile(Game game, MultipartFile file) throws IOException {
        List<Move> moves = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(file.getOriginalFilename()));

        for (String line : lines) {
            if (line.startsWith("#") || line.isBlank()) {
                continue;
            }

            String[] parts = line.split(",");
            if (parts.length < 8) {
                throw new IllegalArgumentException("Invalid line format: " + line);
            }

            Figure[] figureTypes = {
                    new Figure("PAWN", 1),
                    new Figure("HORSE", 5),
                    new Figure("BISHOP", 5),
                    new Figure("ROOK", 5),
                    new Figure("QUEEN", 10),
                    new Figure("KNIGHT", 100)
            };

            Figure figure = Arrays.stream(figureTypes)
                    .filter(f -> f.getName().equalsIgnoreCase(parts[2]))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Figure not found: " + parts[2]));

            Figure eliminatedFigure = null;
            if (!parts[5].isBlank()) {
                eliminatedFigure = Arrays.stream(figureTypes)
                        .filter(f -> f.getName().equalsIgnoreCase(parts[5]))
                        .findFirst()
                        .orElse(null);
            }

            Move move = new Move(
                    game,
                    playerDomainService.getPlayerById(UUID.fromString(parts[1])).orElseThrow(
                            () -> new IllegalArgumentException("Player not found: " + parts[1])),
                    Integer.parseInt(parts[0]),
                    figure,
                    parts[3],
                    parts[4],
                    eliminatedFigure,
                    LocalDateTime.parse(parts[6]),
                    Long.parseLong(parts[7])
            );
            moves.add(move);
        }

        return moves;
    }

    @Override
    public String showEditForm(UUID id, Model model) {
        BaseViewModel baseViewModel = createBaseViewModel("Edit Game");
        Optional<Game> player = gameDomainService.findById(id);
        GameInputModel gameInputModel = player.map(value -> new GameInputModel(
                value.getId(),
                value.getPlayer1().getId(),
                value.getDuration(),
                value.getStartTime(),
                value.getGameType(),
                value.getResult(),
                value.getPlayer2().getId()
        )).orElse(null);

        model.addAttribute("base", baseViewModel);
        model.addAttribute("game", gameInputModel);

        return "player/gameedit";
    }

    @Override
    public String editGame(UUID id, GameInputModel input, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return this.showEditForm(id, model);
        }
        Optional<Player> pl1 = playerDomainService.getPlayerById(input.getPlayer1Id());
        if (pl1.isEmpty()) {
            return this.showCreateForm(model);
        }
        Optional<Player> pl2 = playerDomainService.getPlayerById(input.getPlayer2Id());
        if (pl2.isEmpty()) {
            return this.showCreateForm(model);
        }
        Game game = new Game(
                pl1.get(),
                pl2.get(),
                input.getResult(),
                input.getGameType(),
                input.getStartTime(),
                input.getDuration()
        );
        return "redirect:/player/games";
    }

    @Override
    public String viewGameDetails(UUID gameId, Model model) {
        Optional<Game> gameOptional = gameDomainService.findById(gameId);
        if (gameOptional.isEmpty()) {
            model.addAttribute("error", "Game not found");
            return "error";
        }

        Game game = gameOptional.get();

        List<MoveViewModel> moves = game.getMoves().stream()
                .map(move -> new MoveViewModel(
                        move.getMoveNumber(),
                        move.getPlayer().getName(),
                        move.getFigure().getName(),
                        move.getFromPosition(),
                        move.getToPosition(),
                        move.getEliminatedFigure() != null ? move.getEliminatedFigure().getName() : "None",
                        move.getMoveTime(),
                        move.getDurationMove()
                ))
                .toList();

        GameDetailViewModel viewModel = new GameDetailViewModel(
                new PlayerViewModel(
                        game.getPlayer1().getId(),
                        game.getPlayer1().getName(),
                        game.getPlayer1().getRating(),
                        game.getPlayer1().getChessGrade()
                ),
                new PlayerViewModel(
                        game.getPlayer2().getId(),
                        game.getPlayer2().getName(),
                        game.getPlayer2().getRating(),
                        game.getPlayer2().getChessGrade()
                ),
                game.getResult(),
                game.getGameType(),
                game.getStartTime(),
                game.getDuration(),
                moves
        );

        model.addAttribute("gameDetails", viewModel);
        model.addAttribute("base", createBaseViewModel("Game Details"));
        return "game";
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
