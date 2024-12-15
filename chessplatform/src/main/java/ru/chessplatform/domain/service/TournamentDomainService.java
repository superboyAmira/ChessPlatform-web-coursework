package ru.chessplatform.domain.service;

import com.example.viewmodel.TournamentEntryViewModel;
import com.example.viewmodel.TournamentViewModel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.chessplatform.domain.model.aggregate.Tournament;
import ru.chessplatform.domain.model.aggregate.TournamentEntry;
import ru.chessplatform.domain.repository.TournamentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TournamentDomainService {
    private final TournamentRepository tournamentRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public TournamentDomainService(TournamentRepository tournamentRepository,
                                   RedisTemplate<String, Object> redisTemplate) {
        this.tournamentRepository = tournamentRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<TournamentViewModel> findAll(int limit, int offset) {
        String str = String.format("tournaments:%d:%d", limit, offset);
        List<TournamentViewModel> cachedTournaments = (List<TournamentViewModel>) redisTemplate.opsForValue().get(String.format("tournaments:%d:%d", limit, offset));
        if (cachedTournaments != null) {
            return cachedTournaments;
        }
        List<TournamentViewModel> tournaments = tournamentRepository.findAll(limit, offset)
                .stream()
                .map(tournament -> new TournamentViewModel(
                        tournament.getId(),
                        tournament.getName(),
                        tournament.getStartDate(),
                        tournament.getParticipantCount(),
                        tournament.getTournamentType(),
                        tournament.getPrizePool(),
                        tournament.getStatus(),
                        tournament.getEntries().stream().map(entry -> new TournamentEntryViewModel(
                                entry.getPlayer().getId(),
                                entry.getPlayer().getName(),
                                entry.getPoints(),
                                entry.getGamesPlayed()
                        )).toList()
                ))
                .toList();

        redisTemplate.opsForValue().set(String.format("tournaments:%d:%d", limit, offset), tournaments, 10, TimeUnit.MINUTES);

        return tournaments;
    }

    public Optional<Tournament> findById(UUID id) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
        return tournamentOptional.map(tournament -> {
            tournament.getEntries().sort(Comparator.comparingInt(TournamentEntry::getPoints).reversed());
            return tournament;
        });
    }
    public void save(Tournament entity) {
        tournamentRepository.save(entity);
        redisTemplate.keys("tournaments:*").forEach(redisTemplate::delete);
    }

    public long count() {
        return tournamentRepository.getAmount();
    }
}

