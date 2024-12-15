package ru.chessplatform.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.entity.Player;

public interface TournamentRepository {
    void save(Tournament tournament);
    Optional<Tournament> findById(UUID id);
    List<Tournament> findAll(int limit, int offset);
    List<Tournament> findSpecialTournament();
    Long getAmount();
    List <Tournament> findUpcomingTourmnametns();
}