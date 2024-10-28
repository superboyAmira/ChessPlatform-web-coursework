package ru.chessplatform.infrastructure.hibernate;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.repository.PlayerRepository;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Player player) {
        if (player.getId() == null || !entityManager.contains(player)) {
            entityManager.persist(player);
        } else {
            entityManager.merge(player);
        }
    }

    @Override
    public Optional<Player> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Player.class, id));
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        try {
            Player player = entityManager.createQuery(
                    "SELECT p FROM Player p WHERE p.email = :email", Player.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(player);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

