package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.Player;
import hu.nye.szakdolgozat.data.model.game.Board;
import hu.nye.szakdolgozat.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/szakdolgozat")
public class GameController {
    private final PlayerService playerService;
    private final Board board = new Board();

    @Autowired
    public GameController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/game")
    public String game() {
        return "game";
    }
}