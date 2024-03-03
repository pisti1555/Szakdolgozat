package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.user.User;
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
            session.stepsDone++;
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
            session.stepsDone++;
            service.randomMoveSpider();
        } else {
            System.out.println("Invalid move");
        }

        if (service.whoWon() == 1) {
            session.gamesPlayed++;
            session.gamesWon++;
            updateDB();
        } else if (service.whoWon() == 2) {
            session.gamesPlayed++;
            updateDB();
        }

        return service.whoWon();
    }

    @PostMapping("/playVsFly")
    public int playVsFly(@RequestParam("from")int from, @RequestParam("to")int to) {
        GameService service = session.getGameService();
        if (service.isMoveValid(from, to)) {
            service.move(from, to);
            session.stepsDone++;
            service.randomMoveFly();
        } else {
            System.out.println("Invalid move");
        }

        if (service.whoWon() == 2) {
            session.gamesPlayed++;
            session.gamesWon++;
            updateDB();
        } else if (service.whoWon() == 1) {
            session.gamesPlayed++;
            updateDB();
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

    @PostMapping("/updateDB")
    public User updateDB() {
        User user = session.getUser();
        int wonGames = session.getUser().getWonGames();
        int gamesPlayed = session.getUser().getPlayedGames();
        int stepsDone = session.getUser().getStepsMade();
        user.setWonGames(wonGames + session.gamesWon);
        user.setPlayedGames(gamesPlayed + session.gamesPlayed);
        user.setStepsMade(stepsDone + session.stepsDone);
        if (session.userService.exists(user.getUsername())) {
            session.userService.delete(user.getUsername());
            session.gamesPlayed = 0;
            session.gamesWon = 0;
            session.stepsDone = 0;
            return session.userService.save(user);
        } else return null;
    }
}