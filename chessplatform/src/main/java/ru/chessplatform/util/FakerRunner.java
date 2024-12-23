package ru.chessplatform.util;

import com.github.javafaker.Faker;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.aggregate.TournamentEntry;
import ru.chessplatform.domain.model.entity.Game;
import ru.chessplatform.domain.model.entity.Move;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.model.valueobject.Figure;
import ru.chessplatform.domain.model.valueobject.RoleEnum;
import ru.chessplatform.domain.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Configuration
public class FakerRunner {

    @Bean
    public ApplicationRunner runFaker(
            PlayerRepository playerRepository,
            TournamentRepository tournamentRepository,
            TournamentEntryRepository tournamentEntryRepository,
            GameRepository gameRepository,
            MoveRepository moveRepository) {
        return args -> {
            Faker faker = new Faker();

            List<Tournament> tournaments = new ArrayList<>();
            IntStream.range(0, 10).forEach(i -> {
                Tournament tournament = new Tournament(
                        faker.esports().event(),
                        LocalDateTime.now().plusDays(faker.number().numberBetween(1, 30)),
                        faker.number().numberBetween(8, 32),
                        faker.options().option("Blitz", "Rapid", "Bullet"),
                        faker.number().randomDouble(2, 1000, 10000)
                );
                tournaments.add(tournament);
                tournamentRepository.save(tournament);
            });
            List<Player> players = new ArrayList<>();
            /*Player admin = new Player(
                    "admin",
                    "admin@example.com",
                    0,
                    0,
                    0,
                    "Гроссмейстер",
                    "admin",
                    RoleEnum.ADMIN
            );
            players.add(admin);
            playerRepository.save(admin);

            Player user = new Player(
                    "user",
                    "user@example.com",
                    0,
                    0,
                    0,
                    "3 разряд",
                    "user",
                    RoleEnum.PLAYER
            );
            players.add(user);
            playerRepository.save(user);*/

            IntStream.range(0, 18).forEach(i -> {
                Player randomPlayer = new Player(
                        faker.name().fullName(),
                        faker.internet().emailAddress(),
                        faker.number().numberBetween(1000, 2800), // Рейтинг
                        faker.number().numberBetween(0, 300), // Игры
                        faker.number().numberBetween(0, 500), // Количество побед
                        faker.options().option("Гроссмейстер", "Мастер", "1 разряд", "2 разряд", "3 разряд"),
                        faker.internet().password(),
                        RoleEnum.PLAYER
                );
                players.add(randomPlayer);
                playerRepository.save(randomPlayer);
            });

            String[] figureTypes = {"PAWN", "KNIGHT", "BISHOP", "ROOK", "QUEEN", "KING"};

            tournaments.forEach(tournament -> {
                players.stream()
                        .limit(faker.number().numberBetween(5, tournament.getParticipantCount()))
                        .forEach(player -> {
                            TournamentEntry entry = new TournamentEntry(
                                    tournament,
                                    player,
                                    faker.number().numberBetween(0, 100),
                                    faker.number().numberBetween(0, 10)
                            );
                            tournamentEntryRepository.save(entry);
                        });
            });

            List<Game> games = new ArrayList<>();
            IntStream.range(0, 50)
                    .forEach(i -> {
                        Player player1 = faker.options().nextElement(players);
                        Player player2 = faker.options().nextElement(players);
                        while (player1.equals(player2)) {
                            player2 = faker.options().nextElement(players);
                        }
                        Game game = new Game(
                                player1,
                                player2,
                                faker.options().option("Победа игрока 1", "Победа игрока 2", "Ничья"),
                                faker.options().option("Blitz", "Rapid", "Bullet"),
                                LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30)),
                                faker.number().numberBetween(300, 3600)
                        );
                         gameRepository.save(game);
                    });
            games.forEach(game -> {
                IntStream.range(0, faker.number().numberBetween(10, 100)).forEach(moveNumber -> {
                    Player player = (moveNumber % 2 == 0) ? game.getPlayer1() : game.getPlayer2();
                    Figure figure = new Figure(figureTypes[faker.number().numberBetween(0, figureTypes.length-1)], faker.number().numberBetween(0, 5));
                    Move move = new Move(
                            game,
                            player,
                            moveNumber + 1,
                            figure,
                            faker.number().toString(),
                            faker.number().toString(),
                            faker.bool().bool() ? figure : null,
                            LocalDateTime.now().minusSeconds(faker.number().numberBetween(1, 60)),
                            faker.number().numberBetween(10, 120)
                    );
                    moveRepository.save(move);
                });
            });

            System.out.println("Тестовые данные успешно сгенерированы!");
        };
    }
}