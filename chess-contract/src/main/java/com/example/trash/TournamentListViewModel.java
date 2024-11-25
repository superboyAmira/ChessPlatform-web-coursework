package com.example.trash;

import com.example.viewmodel.TournamentViewModel;

import java.util.List;

public record TournamentListViewModel(
        BaseViewModel base,
        List<TournamentViewModel> tournaments,
        TournamentFilterInputModel filter,
        PaginationViewModel pagination
) {}
