package ru.chessplatform.infrastructure.hibernate;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import ru.chessplatform.domain.model.entity.Move;
import ru.chessplatform.domain.repository.MoveRepository;

@Repository
public class MoveRepositoryImpl extends GeneralRepository<Move> implements MoveRepository {
    public MoveRepositoryImpl() {
        super(Move.class);
    }
}
