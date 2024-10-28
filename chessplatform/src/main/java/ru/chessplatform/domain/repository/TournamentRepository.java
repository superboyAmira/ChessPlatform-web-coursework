package ru.chessplatform.domain.repository;

import java.util.Optional;
import java.util.UUID;

import ru.chessplatform.domain.model.aggregate.Tournament;

public interface TournamentRepository {
    void save(Tournament tournament);
    Optional<Tournament> findById(UUID id);
    Optional<Tournament> findByPlayerId(UUID playerId);
    void updateTournamentEntries(Tournament tournament);
    // как то придумать глобавльный метод для обновления турнирной таблицы через этот репозиторий агрегата
}