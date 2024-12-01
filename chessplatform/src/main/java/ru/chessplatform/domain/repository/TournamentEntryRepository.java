package ru.chessplatform.domain.repository;

import org.springframework.stereotype.Repository;
import ru.chessplatform.domain.model.aggregate.TournamentEntry;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TournamentEntryRepository {
        void save(TournamentEntry tournament);
        Optional<TournamentEntry> findById(UUID id);
        List<TournamentEntry> findAll(int limit, int offset);
        Long getAmount();
}
