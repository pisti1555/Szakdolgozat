package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.game.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/game")
public class GameRestController {
    private final Board gameBoard = new Board();

    @PostMapping("/makeMove")
    public ResponseEntity<String> makeMove(@RequestParam int from, @RequestParam int to) {
        if (gameBoard.isMoveValid(from, to)) {
            gameBoard.makeMove(from, to);
            return ResponseEntity.ok("Move successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid move");
        }
    }

    @PostMapping("/makeRandomMoveSpider")
    public void makeRandomMoveSpider() {
        gameBoard.makeRandomMoveSpider();
    }

    @PostMapping("/playGame")
    public void playGame() {
        
    }
}
