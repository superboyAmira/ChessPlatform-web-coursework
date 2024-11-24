package com.example.trash;

import java.util.List;

public record TournamentListViewModel(
        BaseViewModel base,
        List<TournamentViewModel> tournaments,
        TournamentFilterInputModel filter,
        PaginationViewModel pagination
) {}
