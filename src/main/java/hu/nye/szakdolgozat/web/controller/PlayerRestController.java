package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.Player;
import hu.nye.szakdolgozat.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

/**
 * Javadoc komment.
 */
@RestController
@RequestMapping("/api/player")
public class PlayerRestController {
    private final PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable UUID id) {
        return playerService.getPlayerById(id);
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @PutMapping
    public Player updatePlayer(@RequestBody Player player) {
        return playerService.updatePlayer(player);
    }

    @DeleteMapping("/{id}")
    public void deletePlayerById(@PathVariable UUID id) {
        playerService.deletePlayerById(id);
    }
}