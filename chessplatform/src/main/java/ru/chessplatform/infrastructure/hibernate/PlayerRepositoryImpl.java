package ru.chessplatform.infrastructure.hibernate;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.chessplatform.application.dto.TopTournamentPlayer;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.repository.PlayerRepository;

import javax.transaction.Transactional;

@Repository
public class PlayerRepositoryImpl extends GeneralRepository<Player> implements PlayerRepository {

    public PlayerRepositoryImpl() {
        super(Player.class);
    }

    public List<Player> findByName(String name) {
        return this.getEntityManager().createQuery("SELECT p FROM Player p WHERE p.name = :name", Player.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    @Transactional
    public List<TopTournamentPlayer> findTopTournamentPlayers(int limit) {
        String query = """
    SELECT p.id, p.name, p.chess_grade, SUM(
        CASE 
            WHEN pl.points = 10 THEN 10 
            WHEN pl.points = 5 THEN 5 
            WHEN pl.points = 2 THEN 2 
            ELSE 0 
        END
    ) AS successScore
    FROM player p
    JOIN tournament_entry pl ON p.id = pl.player_id
    GROUP BY p.id, p.name, p.chess_grade
    ORDER BY successScore DESC
    """;

        List<TopTournamentPlayer> res = this.getEntityManager()
                .createNativeQuery(query, "TopTournamentPlayerMapping")
                .setMaxResults(limit)
                .getResultList();
        return res;
    }

    @Override
    public Long getAmountOfPlayers() {
        String query = """
        SELECT COUNT(p) FROM Player p
                """;
        Object result = this.getEntityManager()
                .createNativeQuery(query)
                .getSingleResult();
        return ((Number) result).longValue();
    }
    @Override
    public List<Player> getTopRatingPlayers() {
        String query = """
        SELECT p FROM Player p
        ORDER BY p.rating DESC
    """;
        return this.getEntityManager()
                .createQuery(query, Player.class)
                .setMaxResults(10) // Ограничение на 10 записей
                .getResultList();
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        String query = """
            SELECT p FROM Player p WHERE p.email = :email
        """;
        List<Player> players = this.getEntityManager()
                .createQuery(query, Player.class)
                .setParameter("email", email)
                .getResultList();
        return players.stream().findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        String query = """
            SELECT COUNT(p) FROM Player p WHERE p.email = :email
        """;
        Long count = this.getEntityManager()
                .createQuery(query, Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }
}
