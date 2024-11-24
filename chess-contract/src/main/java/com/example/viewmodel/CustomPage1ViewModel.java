package com.example.viewmodel;


import com.example.trash.PlayerRatingData;
import com.example.trash.TournamentViewModel;

import java.util.List;

public record CustomPage1ViewModel(
        BaseViewModel base,
        List<TournamentViewModel> topPrizeTournaments,
        List<PlayerRatingData> playerRatingsGraphData
) {}