package com.example.trash;

public record PaginationViewModel(
        int currentPage,
        int totalPages,
        long totalItems
) {}