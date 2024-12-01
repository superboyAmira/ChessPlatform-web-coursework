package ru.chessplatform.infrastructure.hibernate;

import org.springframework.stereotype.Repository;
import ru.chessplatform.domain.model.aggregate.TournamentEntry;
import ru.chessplatform.domain.repository.TournamentEntryRepository;


@Repository
public class TournamentEntryImpl extends GeneralRepository<TournamentEntry> implements TournamentEntryRepository {
    public TournamentEntryImpl() {
        super(TournamentEntry.class);
    }

    public Long getAmount() {
        String query = """
        SELECT COUNT(t) FROM TournamentEntry t
    """;
        Object result = this.getEntityManager()
                .createQuery(query)
                .getSingleResult();
        return ((Number) result).longValue();
    }
}
