package hu.nye.szakdolgozat.service;

import hu.nye.szakdolgozat.data.model.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerService {
    Player createPlayer(Player player);
    Player updatePlayer(Player player);
    List<Player> getAllPlayers();
    Player getPlayerById(UUID id);
    void deletePlayerById(UUID id);
}
