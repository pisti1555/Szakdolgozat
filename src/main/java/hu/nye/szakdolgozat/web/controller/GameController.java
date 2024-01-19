package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.Player;
import hu.nye.szakdolgozat.data.model.game.Board;
import hu.nye.szakdolgozat.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {
    private final Board board = new Board();

    @GetMapping()
    public String game() {
        return "game";
    }
}