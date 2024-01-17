package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.game.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/game")
public class GameRestController {
    private final Board gameBoard = new Board();

    @PostMapping("/playWithFly")
    public String playWithFly(@RequestBody Move move) {
        int from = move.getFrom();
        int to = move.getTo();

        if (gameBoard.isMoveValid(from, to)) {
            gameBoard.makeMove(from, to);
            gameBoard.makeRandomMoveSpider();
            return "Moved from " + from + " to " + to;
        } else {
            return "Invalid move";
        }
    }

    @PostMapping("/playWithSpider")
    public String playWithSpider(@RequestBody Move move) {
        int from = move.getFrom();
        int to = move.getTo();

        if (gameBoard.isMoveValid(from, to)) {
            gameBoard.makeMove(from, to);
            gameBoard.makeRandomMoveFly();
            return "Moved from " + from + " to " + to;
        } else {
            return "Invalid move";
        }
    }

    @PostMapping("/makeMove")
    public String makeMove(@RequestBody Move move) {
        int from = move.getFrom();
        int to = move.getTo();

        if (gameBoard.isMoveValid(from, to)) {
            gameBoard.makeMove(from, to);
            return "Moved from " + from + " to " + to;
        } else {
            return "Invalid move";
        }
    }

    @PostMapping("/makeRandomMoveSpider")
    public void makeRandomMoveSpider() {
        gameBoard.makeRandomMoveSpider();
    }

    @PostMapping("/getPositions")
    public int[] sendPositionsToClient() {
        return gameBoard.getPositions();
    }

    @GetMapping("/getConnections")
    public HashMap<Integer, ArrayList<Integer>> getConnections() {
        return gameBoard.getConnections();
    }

    @GetMapping("/isFlysTurn")
    public boolean flysTurn() {
        return gameBoard.isFlysTurn();
    }
}