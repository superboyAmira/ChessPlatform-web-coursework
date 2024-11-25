package com.example.viewmodel;

import java.util.List;

public record MainPageViewModel(
        BaseViewModel base,
        long activePlayersCount,
        long totalGamesPlayed,
        List<TournamentViewModel> upcomingTournaments,
        List<PlayerViewModel> topPlayers,
        List<GameViewModel> recentGrandmasterGames
) {}