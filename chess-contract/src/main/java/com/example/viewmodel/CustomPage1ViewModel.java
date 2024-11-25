package com.example.viewmodel;


import java.util.List;

public record CustomPage1ViewModel(
        BaseViewModel base,
        List<TournamentViewModel> topPrizeTournaments,
        List<TopTournamentPlayerViewModel> playerTopTournamentGraphData
) {}