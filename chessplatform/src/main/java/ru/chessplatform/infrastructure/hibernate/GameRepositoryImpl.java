package ru.chessplatform.infrastructure.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.entity.Game;
import ru.chessplatform.domain.repository.GameRepository;

@Repository
public class GameRepositoryImpl implements GameRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Game game) {
        if (findById(game.getId()).isPresent()) {
            entityManager.merge(game);
        } else {
            entityManager.persist(game);
        }
    }

    @Override
    public Optional<Game> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Game.class, id));
    }

    @Override
    public List<Game> findByPlayerId(UUID playerId) {
        return entityManager.createQuery(
                "SELECT g FROM Game g WHERE g.player1.id = :playerId OR g.player2.id = :playerId", Game.class)
                .setParameter("playerId", playerId)
                .getResultList();
    }
}
