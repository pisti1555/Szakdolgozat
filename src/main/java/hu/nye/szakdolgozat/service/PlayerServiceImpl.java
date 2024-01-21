package hu.nye.szakdolgozat.service;

import hu.nye.szakdolgozat.data.model.Player;
import hu.nye.szakdolgozat.data.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService{

    private final PlayerRepository<Player, UUID> playerRepository;
    @Autowired
    public PlayerServiceImpl(PlayerRepository<Player, UUID> playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player player) {
        return playerRepository.update(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.getAll();
    }

    @Override
    public Player getPlayerById(UUID id) {
        return playerRepository.getById(id);
    }

    @Override
    public void deletePlayerById(UUID id) {
        playerRepository.deleteById(id);
    }
}
