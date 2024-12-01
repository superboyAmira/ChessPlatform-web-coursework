package ru.chessplatform.infrastructure.hibernate;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.entity.Game;
import ru.chessplatform.domain.repository.GameRepository;

@Repository
public class GameRepositoryImpl extends GeneralRepository<Game> implements GameRepository {
    public GameRepositoryImpl() {
        super(Game.class);
    }

    @Override
    public List<Game> findByPlayerId(UUID playerId) {
        return this.getEntityManager().createQuery(
                "SELECT g FROM Game g WHERE g.player1.id = :playerId OR g.player2.id = :playerId", Game.class)
                .setParameter("playerId", playerId)
                .getResultList();
    }

    @Override
    public List<Game> findGMGames() {
        String query = "SELECT g " +
                       "FROM Game g " +
                       "JOIN Player p " +
                       "ON p.id = g.player1 OR p.id = g.player2 " +
                       "WHERE p.chessGrade = 'Гроссмейстер' " +
                       "ORDER BY g.startTime DESC";
        return this.getEntityManager()
                .createQuery(query).setMaxResults(5).getResultList();
    }

    @Override
    public Long getCountAllGames() {
        String query = """
        SELECT COUNT(g) FROM Game g
                """;
        Object result = this.getEntityManager()
                .createNativeQuery(query)
                .getSingleResult();
        return ((Number) result).longValue();
    }
}
