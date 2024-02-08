package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }


    @PostMapping("/playVsPlayer")
    public int playVsPlayer(@RequestParam("from")int from, @RequestParam("to")int to) {
        if (service.isMoveValid(from, to)) {
            service.move(from, to);
        } else {
            System.out.println("Invalid move");
        }

        return service.whoWon();
    }

    @PostMapping("/playVsSpider")
    public int playVsSpider(@RequestParam("from")int from, @RequestParam("to")int to) {
        if (service.isMoveValid(from, to)) {
            service.move(from, to);
            service.randomMoveSpider();
        } else {
            System.out.println("Invalid move");
        }

        return service.whoWon();
    }

    @PostMapping("/playVsFly")
    public int playVsFly(@RequestParam("from")int from, @RequestParam("to")int to) {
        if (service.isMoveValid(from, to)) {
            service.move(from, to);
            service.randomMoveFly();
        } else {
            System.out.println("Invalid move");
        }

        return service.whoWon();
    }

    @PostMapping("/getPositions")
    public int[] sendPositionsToClient() {
        return service.getPositions();
    }

    @GetMapping("/getConnections")
    public HashMap<Integer, ArrayList<Integer>> getConnections() {
        return service.getConnections();
    }

    @GetMapping("/getGameMode")
    public short getGameMode() {
        return service.getGameMode();
    }

    @GetMapping("/isFlysTurn")
    public boolean isFlysTurn() {
        return service.isFlysTurn();
    }

    @PostMapping("/newGame")
    public void newGame(@RequestParam("mode") String mode) {
        service.newGame(mode);
    }

    @GetMapping("/isGameRunning")
    public boolean isGameRunning() {
        return service.getIsGameRunning();
    }

    @GetMapping("/getFlyStepsDone")
    public int getStepsDone() {
        return service.getFlyStepsDone();
    }

    @GetMapping("/getSpiderStepsDone")
    public int getSpiderStepsDone() {
        return service.getSpiderStepsDone();
    }
}