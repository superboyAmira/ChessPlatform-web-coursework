package ru.chessplatform.infrastructure.hibernate;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.aggregate.TournamentEntry;
import ru.chessplatform.domain.repository.TournamentRepository;

@Repository
public class TournamentRepositoryImpl implements TournamentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Tournament tournament) {
        if (tournament.getId() == null || !entityManager.contains(tournament)) {
            entityManager.persist(tournament);
        } else {
            entityManager.merge(tournament);
        }
    }

    @Override
    public Optional<Tournament> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Tournament.class, id));
    }

    @Override
    public Optional<Tournament> findByPlayerId(UUID playerId) {
        try {
            Tournament tournament = entityManager.createQuery(
                    "SELECT t FROM Tournament t JOIN t.entries e WHERE e.player.id = :playerId", Tournament.class)
                    .setParameter("playerId", playerId)
                    .getSingleResult();
            return Optional.of(tournament);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // TODO: переписать под пактеную вставку
    @Override
    @Transactional
    public void updateTournamentEntries(Tournament tournament) {
        Tournament managedTournament = entityManager.find(Tournament.class, tournament.getId());
        if (managedTournament != null) {
            managedTournament.getEntries().clear();
            for (TournamentEntry entry : tournament.getEntries()) {
                managedTournament.getEntries().add(entry);
                entityManager.merge(entry);
            }
        } else {
            throw new IllegalArgumentException("Tournament not found: " + tournament.getId());
        }
    }
}
