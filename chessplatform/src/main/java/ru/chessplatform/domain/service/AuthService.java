package ru.chessplatform.domain.service;

import org.springframework.stereotype.Service;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.model.valueobject.RoleEnum;
import ru.chessplatform.domain.repository.PlayerRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final PlayerRepository playerRepository;

    public AuthService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void registerUser(String name, String email, String password, RoleEnum role, String chessGrade) {
        if (playerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        Player player = new Player();
        player.setName(name);
        player.setEmail(email);
        player.setPassword("{noop}" + password); // Указываем {noop}, чтобы пароли не хешировались
        player.setRole(role);
        player.setChessGrade(chessGrade);
        playerRepository.save(player);
    }

    public Optional<Player> getUserById(UUID id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> getUserByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    public boolean userExists(String email) {
        return playerRepository.existsByEmail(email);
    }
}
