package ru.chessplatform.infrastructure.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.aggregate.TournamentEntry;
import ru.chessplatform.domain.repository.TournamentRepository;

@Repository
public class TournamentRepositoryImpl extends GeneralRepository<Tournament> implements TournamentRepository {

    public TournamentRepositoryImpl() {
        super(Tournament.class);
    }

    @Override
    public Optional<Tournament> findByPlayerId(UUID playerId) {
        try {
            Tournament tournament = this.getEntityManager().createQuery(
                    "SELECT t FROM Tournament t JOIN t.entries e WHERE e.player.id = :playerId", Tournament.class)
                    .setParameter("playerId", playerId)
                    .getSingleResult();
            return Optional.of(tournament);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void updateTournamentEntries(Tournament tournament) {
        Tournament managedTournament = this.getEntityManager().find(Tournament.class, tournament.getId());
        if (managedTournament != null) {
            managedTournament.getEntries().clear();
            for (TournamentEntry entry : tournament.getEntries()) {
                managedTournament.getEntries().add(entry);
                this.getEntityManager().merge(entry);
            }
        } else {
            throw new IllegalArgumentException("Tournament not found: " + tournament.getId());
        }
    }

    @Override
    public List<Tournament> findAll(int limit, int offset) {
        return this.getEntityManager().createQuery("SELECT t FROM Tournament t ORDER BY t.startDate DESC", Tournament.class).getResultList();
    }
}
