package ru.chessplatform.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.example.viewmodel.PlayerViewModel;
import com.example.viewmodel.TournamentViewModel;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.model.valueobject.RoleEnum;
import ru.chessplatform.domain.repository.PlayerRepository;

@Service
public class PlayerDomainService {
    private final PlayerRepository playerRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public PlayerDomainService(PlayerRepository playerRepository, RedisTemplate<String, Object> redisTemplate) {
        this.playerRepository = playerRepository;
        this.redisTemplate = redisTemplate;
    }

    public Long count() {
        return playerRepository.getAmountOfPlayers();
    }
    public Optional<Player> getPlayerById(UUID id) {
        return playerRepository.findById(id);
    }
    public List<PlayerViewModel> findAll(int limit, int offset) {
        List<PlayerViewModel> cachedPlayers = (List<PlayerViewModel>) redisTemplate.opsForValue().get(String.format("players:%d:%d", limit, offset));
        if (cachedPlayers != null) {
            return cachedPlayers;
        }
         List<PlayerViewModel> players = playerRepository.findAll(limit, offset)
                .stream()
                .map(value -> new PlayerViewModel(
                        value.getId(),
                        value.getName(),
                        value.getRating(),
                        value.getChessGrade()
                )).toList();
        redisTemplate.opsForValue().set(String.format("players:%d:%d", limit, offset), players, 10, TimeUnit.MINUTES);
        return players;
    }

    public void save(Player player) {
        playerRepository.save(player);
        redisTemplate.keys("players:*").forEach(redisTemplate::delete);
    }

    public Optional<Player> findById(UUID id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            return Optional.empty();
        }
        if (player.get().getRole() == RoleEnum.ADMIN) {
            return Optional.empty();
        }
        return player;
    }

    public List<Player> findByNameContaining(String name) {
        List<Player> players = playerRepository.findByName(name);
        List<Player> result = new ArrayList<>();
        players.forEach(player -> {
            if (player.getRole() != RoleEnum.ADMIN) {
                result.add(player);
            }
        });
        return result;
    }

    public Optional<Player> getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email);
    }
}
