package hu.nye.szakdolgozat.service;

import hu.nye.szakdolgozat.data.model.game.Board;
import hu.nye.szakdolgozat.data.model.game.Piece;
import hu.nye.szakdolgozat.data.model.game.Pieces;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {
    private Board board;

    private short gameMode;
    private final short PVP = 1;
    private final short PVS = 2;
    private final short PVF = 3;
    private final Random random = new Random();
    private boolean isGameRunning = false;
    private boolean isFlysTurn = true;
    private int pieceWon = 0;
    private int flyStepsDone = 0;
    private int spiderStepsDone = 0;

    @Autowired
    public GameServiceImpl() {
        this.board = new Board();
    }


    //---------------- Main methods to use in Controller ----------------
    @Override
    public Board getBoard() {
        return this.board;

    }

    @Override
    public void setBoard(Board b) {
        this.board = b;
    }



    /**
     * performs move on game board, adds +1 to steps done
     * @param from - number of field to move from
     * @param to - number of field to move to
     * @return - true if someone won
     */
    @Override
    public int move(int from, int to) {
        if (whichPiece(from) == board.getFly()) {
            moveFly(from, to);
            flyStepsDone++;
        }
        for (Piece p : board.getSpider()) {
            if (whichPiece(from) == p) {
                moveSpider(from, to, p);
                spiderStepsDone++;
            }
        }

        return whoWon();
    }

    @Override
    public boolean randomMoveFly() {
        int availableFields = board.getField()[board.getFly().location].getConnection().length;
        for (int i = 0; i < availableFields; i++) {
            if(board.getField()[board.getFly().location]
                    .getConnection()[i] == null) availableFields--;
        }

        if (isGameRunning) {
            int randomConnection = random.nextInt(availableFields);
            int randomField = board.getField()[board.getFly().location]
                    .getConnection()[randomConnection].getNumber();

            if(isMoveValid(board.getFly().location, randomField)) {
                moveFly(board.getFly().location, randomField);
                return true;
            } else {
                randomMoveFly();
            }
        }
        return false;
    }

    @Override
    public boolean randomMoveSpider() {
        Piece randomSpider = board.getSpider()[random.nextInt(board.getSpider().length)];

        int unavailableFields = 0;
        for (int i = 0; i < board.getField()[randomSpider.location].getConnection().length; i++) {
            if(board.getField()[randomSpider.location]
                    .getConnection()[i] == null) unavailableFields++;
        }
        int availableFields = 6 - unavailableFields;
        int randomConnection = random.nextInt(availableFields);
        int randomField = board.getField()[randomSpider.location]
                .getConnection()[randomConnection].getNumber();

        if (isGameRunning) {
            if(isMoveValid(randomSpider.location, randomField)) {
                moveSpider(randomSpider.location, randomField, randomSpider);
                return true;
            } else {
                randomMoveSpider();
            }
        }
        return false;
    }

    @Override
    public boolean isMoveValid(int from, int to) {
        boolean correctPiece = false;
        if(isFlysTurn) {
            if (from == board.getFly().location) {
                correctPiece = true;
            } else return false;
        } else {
            for (int i = 0; i < board.getSpider().length; i++) {
                if (from == board.getSpider()[i].location) correctPiece = true;
            }
        }

        boolean areFieldsConnected = false;
        boolean isFromFieldEmpty = board.getField()[from].getPiece() == Pieces.EMPTY;
        boolean isToFieldEmpty = board.getField()[to].getPiece() == Pieces.EMPTY;

        for (int i = 0; i < board.getField()[from].getConnection().length; i++) {
            if (board.getField()[from].getConnection()[i] == board.getField()[to]) {
                areFieldsConnected = true;
            }
        }

        return isGameRunning() && !isFromFieldEmpty && isToFieldEmpty && correctPiece && areFieldsConnected;
    }

    @Override
    public int[] getPositions() {
        int[] loc = new int[board.getSpider().length + 1];
        loc[0] = board.getFly().location;
        for (int i = 1; i < board.getSpider().length + 1; i++) {
            loc[i] = board.getSpider()[i-1].location;
        }

        return loc;
    }

    @Override
    public short getGameMode() {
        return this.gameMode;
    }

    @Override
    public HashMap<Integer, ArrayList<Integer>> getConnections() {
        HashMap<Integer, ArrayList<Integer>> connections = new HashMap<>();
        for (int i = 0; i < board.getField().length; i++) {
            int numberOfConnections = 6;
            for (int j = 0; j < 6; j++) {
                if (board.getField()[i].getConnection()[j] == null) numberOfConnections--;
            }

            ArrayList<Integer> connectionOfField = new ArrayList<>();

            for (int j = 0; j < numberOfConnections; j++) {
                connectionOfField.add(board.getField()[i].getConnection()[j].getNumber());
                connections.put(i, connectionOfField);
            }
        }

        for (Map.Entry<Integer, ArrayList<Integer>> entry : connections.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Integer> values = entry.getValue();
        }

        return connections;
    }


    //---------------- Child methods of Main methods listed above ----------------
    @Override
    public void moveFly(int from, int to) {
        if (isMoveValid(from, to)) {
            board.getField()[to].setPiece(board.getField()[from].getPiece());
            board.getField()[from].setPiece(Pieces.EMPTY);

            board.getFly().location = to;
            isFlysTurn = !isFlysTurn;
        } else {
            System.out.println("Invalid");
        }
        display();
    }

    @Override
    public void moveSpider(int from, int to, Piece p) {
        if (isMoveValid(from, to)) {
            board.getField()[to].setPiece(board.getField()[from].getPiece());
            board.getField()[from].setPiece(Pieces.EMPTY);

            p.location = to;
            isFlysTurn = !isFlysTurn;
        } else {
            System.out.println("Invalid");
        }
        display();
    }

    @Override
    public Piece whichPiece(int location) {
        Piece pieceToReturn = null;
        for (Piece p: board.getSpider()) {
            if (p.location == location) pieceToReturn = p;
        }
        if (board.getFly().location == location) pieceToReturn = board.getFly();

        return pieceToReturn;
    }

    public boolean isGameRunning() {
        if (
                board.getFly().location == 0 ||
                        board.getFly().location == 5 ||
                        board.getFly().location == 10 ||
                        board.getFly().location == 14 ||
                        board.getFly().location == 18 ||
                        board.getFly().location == 22
        ) {
            isGameRunning = false;
            pieceWon = 1;
        }

        int unavailableFields = 0;
        for (int i = 0; i < board.getField()[board.getFly().location].getConnection().length; i++) {
            if(
                    board.getField()[board.getFly().location].getConnection()[i] == null ||
                            board.getField()[board.getFly().location].getConnection()[i].getPiece() != Pieces.EMPTY
            ) unavailableFields++;
        }

        if (unavailableFields >= board.getField()[board.getFly().location].getConnection().length) {
            isGameRunning = false;
            pieceWon = 2;
        }
        return isGameRunning;
    }

    @Override
    public boolean isFlysTurn() {
        return isFlysTurn;
    }

    @Override
    public void display() {
        int flyLoc = -1;
        int spiderArrayIndex = 0;
        int[] spiderLoc = new int[board.getSpider().length];

        for (int i = 0; i < board.getField().length; i++) {
            if(board.getField()[i].getPiece() == Pieces.FLY) {
                System.out.print("F ");
                flyLoc = i;
            } else if(board.getField()[i].getPiece() == Pieces.SPIDER) {
                System.out.print("S ");
                spiderLoc[spiderArrayIndex] = i;
                spiderArrayIndex++;
            } else {
                System.out.print("- ");
            }
        }
        System.out.println("\nPiece objects' locations: ");
        System.out.println("Fly location: " + board.getFly().location);
        for (int i = 0; i < board.getSpider().length; i++) {
            System.out.println("Spider[" + i + "] location: " + board.getSpider()[i].location);
        }

        System.out.println("\nBoard's locations: ");
        System.out.println("Fly found on field number " + flyLoc);
        for (int j : spiderLoc) {
            System.out.println("Spider found on field number " + j);
        }
        System.out.println("\n isFlysTurn: " + isFlysTurn + "\n");
    }

    @Override
    public boolean newGame(String gameMode) {
        switch (gameMode) {
            case "pvp": {
                this.gameMode = PVP;
                this.board = new Board();
                isGameRunning = true;
                pieceWon = 0;
                isFlysTurn = true;
                flyStepsDone = 0;
                spiderStepsDone = 0;
                return true;
            }
            case "pvs": {
                this.gameMode = PVS;
                this.board = new Board();
                isGameRunning = true;
                pieceWon = 0;
                isFlysTurn = true;
                flyStepsDone = 0;
                spiderStepsDone = 0;
                return true;
            }
            case "pvf": {
                this.gameMode = PVF;
                this.board = new Board();
                isGameRunning = true;
                pieceWon = 0;
                isFlysTurn = false;
                flyStepsDone = 0;
                spiderStepsDone = 0;
                return true;
            }
            default: return false;
        }
    }

    @Override
    public boolean getIsGameRunning() {
        return isGameRunning;
    }


    /**
     * pieceWon's value is 0 if no one won yet
     *                      1 if fly won
     *                      2 if spiders won
     * @return value of pieceWon
     */
    @Override
    public int whoWon() {
        isGameRunning();
        return pieceWon;
    }

    @Override
    public int getFlyStepsDone() {
        return flyStepsDone;
    }

    @Override
    public int getSpiderStepsDone() {
        return spiderStepsDone;
    }











}
