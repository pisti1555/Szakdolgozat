package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/game")
public class GameRestController {
    private final GameService service;

    @Autowired
    public GameRestController(GameService service) {
        this.service = service;
    }

    @PostMapping("/playVsPlayer")
    public String playVsPlayer(@RequestBody Move move) {
        int from = move.getFrom();
        int to = move.getTo();

        if (service.isMoveValid(from, to)) {
            service.move(from, to);
            return "Moved from " + from + " to " + to;
        } else {
            return "Invalid move";
        }
    }

    @PostMapping("/playVsSpider")
    public String playVsSpider(@RequestBody Move move) {
        int from = move.getFrom();
        int to = move.getTo();

        if (service.isMoveValid(from, to)) {
            service.move(from, to);
            service.randomMoveSpider();
            return "Moved from " + from + " to " + to;
        } else {
            return "Invalid move";
        }
    }

    @PostMapping("/playVsFly")
    public String playVsFly(@RequestBody Move move) {
        int from = move.getFrom();
        int to = move.getTo();

        if (service.isMoveValid(from, to)) {
            service.move(from, to);
            service.randomMoveFly();
            return "Moved from " + from + " to " + to;
        } else {
            return "Invalid move";
        }
    }

    @PostMapping("/makeMove")
    public String makeMove(@RequestBody Move move) {
        int from = move.getFrom();
        int to = move.getTo();

        if (service.isMoveValid(from, to)) {
            service.move(from, to);
            return "Moved from " + from + " to " + to;
        } else {
            return "Invalid move";
        }
    }

    @PostMapping("/makeRandomMoveSpider")
    public void makeRandomMoveSpider() {
        service.randomMoveSpider();
    }

    @PostMapping("/getPositions")
    public int[] sendPositionsToClient() {
        return service.getPositions();
    }

    @GetMapping("/getConnections")
    public HashMap<Integer, ArrayList<Integer>> getConnections() {
        return service.getConnections();
    }

    @GetMapping("/isFlysTurn")
    public boolean isFlysTurn() {
        return service.isFlysTurn();
    }

    @GetMapping("/newGame")
    public void newGame() {
        service.newGame();
    }
}