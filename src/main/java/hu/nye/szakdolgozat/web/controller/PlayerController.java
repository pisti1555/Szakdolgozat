package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.Player;
import hu.nye.szakdolgozat.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/szakdolgozat")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/create")
    public String createPlayer() {
        return "szakdolgozat/create";
    }

    @PostMapping("/create")
    public String createPlayer(Model model, Player player) {
        Player newPlayer = playerService.createPlayer(player);
        model.addAttribute("player", newPlayer);
        return "szakdolgozat/edit";
    }
}
