package hu.nye.szakdolgozat.service;

import hu.nye.szakdolgozat.data.model.game.Board;
import hu.nye.szakdolgozat.data.model.game.Piece;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

public interface GameService {
    Board getBoard();
    void setBoard(Board b);

    int move(int from, int to);
    boolean randomMoveFly();
    boolean randomMoveSpider();

    boolean isMoveValid(int from, int to);
    int[] getPositions();
    short getGameMode();
    HashMap<Integer, ArrayList<Integer>> getConnections();

    void moveFly(int from, int to);
    void moveSpider(int from, int to, Piece piece);
    Piece whichPiece(int location);
    boolean isFlysTurn();
    void display();

    boolean newGame(String gameMode);
    boolean getIsGameRunning();
    int whoWon();
    int getFlyStepsDone();
    int getSpiderStepsDone();
}
