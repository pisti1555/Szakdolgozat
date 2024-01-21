package hu.nye.szakdolgozat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {

    @GetMapping()
    public String game() {
        return "game";
    }
}