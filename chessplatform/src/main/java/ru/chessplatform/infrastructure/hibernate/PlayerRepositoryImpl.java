package ru.chessplatform.infrastructure.hibernate;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.repository.PlayerRepository;

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
}

