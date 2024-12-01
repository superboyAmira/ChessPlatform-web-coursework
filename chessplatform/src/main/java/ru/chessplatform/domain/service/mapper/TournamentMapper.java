package ru.chessplatform.domain.service.mapper;

import com.example.viewmodel.TournamentEntryViewModel;
import com.example.viewmodel.TournamentViewModel;
import ru.chessplatform.domain.model.aggregate.Tournament;

import java.util.stream.Collectors;

public class TournamentMapper {
    public static TournamentViewModel toViewModel(Tournament tournament) {
        return new TournamentViewModel(
                tournament.getId(),
                tournament.getName(),
                tournament.getStartDate(),
                tournament.getParticipantCount(),
                tournament.getTournamentType(),
                tournament.getPrizePool(),
                tournament.getStatus(),
                tournament.getEntries()
                        .stream()
                        .map(entry -> new TournamentEntryViewModel(
                                entry.getPlayer().getId(),
                                entry.getPlayer().getName(),
                                entry.getPoints(),
                                entry.getGamesPlayed()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
