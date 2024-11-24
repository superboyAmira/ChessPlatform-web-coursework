package ru.chessplatform.domain.service;

import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.repository.TournamentRepository;

import java.util.List;

public class TournamentDomainService {
    private final TournamentRepository tournamentRepository;

    public TournamentDomainService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getUpcomingTournaments(int limit) {
         return tournamentRepository.findAll(limit, 0);
    }

    // public List<Tournament> getActiveTournaments() {
    //     return tournamentRepository.findAll().stream()
    //     .filter(tournament -> tournament != null)
    //     .sorted((g1, g2) -> g2.getStartTime().compareTo(g1.getStartTime()))
    //     .limit(limit)
    //     .toList();
    // }
}

