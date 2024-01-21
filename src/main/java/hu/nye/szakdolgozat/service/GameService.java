package hu.nye.szakdolgozat.service;

import hu.nye.szakdolgozat.data.model.game.Board;
import hu.nye.szakdolgozat.data.model.game.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public interface GameService {
    Board getBoard();
    void setBoard(Board b);

    void move(int from, int to);
    boolean randomMoveFly();
    boolean randomMoveSpider();

    boolean isMoveValid(int from, int to);
    int[] getPositions();
    HashMap<Integer, ArrayList<Integer>> getConnections();

    void moveFly(int from, int to);
    void moveSpider(int from, int to, Piece piece);
    Piece whichPiece(int location);
    boolean isFlysTurn();
    void display();


}
