package com.example.viewmodel;

import com.example.trash.GameViewModel;
import com.example.trash.PlayerViewModel;
import com.example.trash.TournamentViewModel;

import java.util.List;

public record MainPageViewModel(
        BaseViewModel base,
        long activePlayersCount,
        long totalGamesPlayed,
        List<TournamentViewModel> upcomingTournaments,
        List<PlayerViewModel> topPlayers,
        List<GameViewModel> recentGrandmasterGames
) {}