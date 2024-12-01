package ru.chessplatform.domain.service;

import org.springframework.stereotype.Service;
import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.repository.TournamentRepository;
import ru.chessplatform.infrastructure.hibernate.TournamentRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TournamentDomainService {
    private final TournamentRepository tournamentRepository;

    public TournamentDomainService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getUpcomingTournaments(int limit) {
         return tournamentRepository.findAll(limit, 0);
    }

    public List<Tournament> getHotTournaments() {
         return tournamentRepository.findSpecialTournament();
    }

    public List<Tournament> findAll(int limit, int offset) {
        return tournamentRepository.findAll(limit, offset);
    }

    public Optional<Tournament> findById(UUID id) {
        return tournamentRepository.findById(id);
    }

    public void save(Tournament entity) {
        tournamentRepository.save(entity);
    }

    public long count() {
        return tournamentRepository.getAmount();
    }

    public void update(UUID id, Tournament updatedEntity) {
        tournamentRepository.save(updatedEntity);
    }
}

