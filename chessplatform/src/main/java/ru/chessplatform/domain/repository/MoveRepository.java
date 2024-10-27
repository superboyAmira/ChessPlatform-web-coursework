package ru.chessplatform.domain.repository;

import java.util.List;
import java.util.UUID;

import ru.chessplatform.domain.model.entity.Move;

public interface MoveRepository {
    void save(Move move);
    List<Move> findAllByGame(UUID gameId);
}
