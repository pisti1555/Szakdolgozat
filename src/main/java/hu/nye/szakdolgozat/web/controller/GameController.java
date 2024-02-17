package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final Session session;

    @Autowired
    public GameController(Session session) {
        this.session = session;
    }

    @PostMapping("/playVsPlayer")
    public int playVsPlayer(@RequestParam("from")int from, @RequestParam("to")int to) {
        GameService service = session.getGameService();
        if (service.isMoveValid(from, to)) {
            service.move(from, to);
        } else {
            System.out.println("Invalid move");
        }

        return service.whoWon();
    }

    @PostMapping("/playVsSpider")
    public int playVsSpider(@RequestParam("from")int from, @RequestParam("to")int to) {
        GameService service = session.getGameService();
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
        GameService service = session.getGameService();
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
        GameService service = session.getGameService();
        return service.getPositions();
    }

    @GetMapping("/getConnections")
    public HashMap<Integer, ArrayList<Integer>> getConnections() {
        GameService service = session.getGameService();
        return service.getConnections();
    }

    @GetMapping("/getGameMode")
    public short getGameMode() {
        GameService service = session.getGameService();
        return service.getGameMode();
    }

    @GetMapping("/isFlysTurn")
    public boolean isFlysTurn() {
        GameService service = session.getGameService();
        return service.isFlysTurn();
    }

    @PostMapping("/newGame")
    public void newGame(@RequestParam("mode") String mode) {
        GameService service = session.getGameService();
        service.newGame(mode);
    }


    @GetMapping("/isGameRunning")
    public boolean isGameRunning() {
        GameService service = session.getGameService();
        return service.getIsGameRunning();
    }

    @GetMapping("/getFlyStepsDone")
    public int getStepsDone() {
        GameService service = session.getGameService();
        return service.getFlyStepsDone();
    }

    @GetMapping("/getSpiderStepsDone")
    public int getSpiderStepsDone() {
        GameService service = session.getGameService();
        return service.getSpiderStepsDone();
    }
}