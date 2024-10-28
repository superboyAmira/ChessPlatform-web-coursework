package ru.chessplatform.infrastructure.hibernate;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.entity.Move;
import ru.chessplatform.domain.repository.MoveRepository;

@Repository
public class MoveRepositoryImpl implements MoveRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Move move) {
        if (move.getId() == null || !entityManager.contains(move)) {
            entityManager.persist(move);
        } else {
            entityManager.merge(move);
        }
    }

    @Override
    public List<Move> findAllByGame(UUID gameId) {
        return entityManager.createQuery(
                "SELECT m FROM Move m WHERE m.game.id = :gameId", Move.class)
                .setParameter("gameId", gameId)
                .getResultList();
    }
}
