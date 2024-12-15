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
    @Transactional
    public List<Tournament> findSpecialTournament() {
        String query = """
        SELECT t FROM Tournament t
        JOIN t.entries e
        JOIN e.player p
        WHERE p.chessGrade = :grandmasterGrade
        GROUP BY t.id
        ORDER BY COUNT(e.id) DESC, t.prizePool DESC, SIZE(t.entries) DESC
    """;

        return this.getEntityManager().createQuery(query, Tournament.class)
                .setParameter("grandmasterGrade", "Гроссмейстер")
                .setMaxResults(6)
                .getResultList();
    }

    @Override
    public Long getAmount() {
        String query = """
        SELECT COUNT(t) FROM Tournament t
    """;
        Object result = this.getEntityManager()
                .createQuery(query)
                .getSingleResult();
        return ((Number) result).longValue();
    }

    public List<Tournament> findUpcomingTourmnametns() {
        String query = """
            SELECT t FROM Tournament t
            ORDER BY startDate
        """;

        return this.getEntityManager().createQuery(query, Tournament.class)
                    .setMaxResults(10)
                    .getResultList();
    }
}
