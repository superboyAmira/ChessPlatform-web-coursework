package ru.chessplatform.infrastructure.hibernate;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.repository.PlayerRepository;

import javax.transaction.Transactional;

@Repository
public class PlayerRepositoryImpl extends GeneralRepository<Player> implements PlayerRepository {

    public PlayerRepositoryImpl() {
        super(Player.class);
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        try {
            Player player = this.getEntityManager().createQuery(
                            "SELECT p FROM Player p WHERE p.email = :email", Player.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(player);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<Object[]> findTopTournamentPlayers(int limit) {
        String query = """
                    SELECT p.name, p.chess_grade, SUM(
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

        List<Object[]> res = this.getEntityManager().createNativeQuery(query)
                .setMaxResults(limit)
                .getResultList();
        return  res;
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
}
