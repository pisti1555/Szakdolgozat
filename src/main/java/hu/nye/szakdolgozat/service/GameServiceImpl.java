package hu.nye.szakdolgozat.service;

import hu.nye.szakdolgozat.data.model.game.Board;
import hu.nye.szakdolgozat.data.model.game.Piece;
import hu.nye.szakdolgozat.data.model.game.Pieces;
import hu.nye.szakdolgozat.data.repository.GameRepository;
import org.apache.catalina.valves.JsonErrorReportValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository board;
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
    public GameServiceImpl(GameRepository board) {
        this.board = board;
    }

    //---------------- Main methods to use in Controller ----------------
    @Override
    public Board getBoard() {
        return board.getBoard();
    }

    @Override
    public void setBoard(Board b) {
        board.setBoard(b);
    }



    /**
     * performs move on game board, adds +1 to steps done
     * @param from - number of field to move from
     * @param to - number of field to move to
     * @return - true if someone won
     */
    @Override
    public int move(int from, int to) {
        if (whichPiece(from) == board.getBoard().getFly()) {
            moveFly(from, to);
            flyStepsDone++;
        }
        for (Piece p : board.getBoard().getSpider()) {
            if (whichPiece(from) == p) {
                moveSpider(from, to, p);
                spiderStepsDone++;
            }
        }

        return whoWon();
    }

    @Override
    public boolean randomMoveFly() {
        int availableFields = board.getBoard().getField()[board.getBoard().getFly().location].getConnection().length;
        for (int i = 0; i < availableFields; i++) {
            if(board.getBoard().getField()[board.getBoard().getFly().location]
                    .getConnection()[i] == null) availableFields--;
        }

        if (isGameRunning) {
            int randomConnection = random.nextInt(availableFields);
            int randomField = board.getBoard().getField()[board.getBoard().getFly().location]
                    .getConnection()[randomConnection].getNumber();

            if(isMoveValid(board.getBoard().getFly().location, randomField)) {
                moveFly(board.getBoard().getFly().location, randomField);
                return true;
            } else {
                randomMoveFly();
            }
        }
        return false;
    }

    @Override
    public boolean randomMoveSpider() {
        Piece randomSpider = board.getBoard().getSpider()[random.nextInt(board.getBoard().getSpider().length)];

        int unavailableFields = 0;
        for (int i = 0; i < board.getBoard().getField()[randomSpider.location].getConnection().length; i++) {
            if(board.getBoard().getField()[randomSpider.location]
                    .getConnection()[i] == null) unavailableFields++;
        }
        int availableFields = 6 - unavailableFields;
        int randomConnection = random.nextInt(availableFields);
        int randomField = board.getBoard().getField()[randomSpider.location]
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
            if (from == board.getBoard().getFly().location) {
                correctPiece = true;
            } else return false;
        } else {
            for (int i = 0; i < board.getBoard().getSpider().length; i++) {
                if (from == board.getBoard().getSpider()[i].location) correctPiece = true;
            }
        }

        boolean areFieldsConnected = false;
        boolean isFromFieldEmpty = board.getBoard().getField()[from].getPiece() == Pieces.EMPTY;
        boolean isToFieldEmpty = board.getBoard().getField()[to].getPiece() == Pieces.EMPTY;

        for (int i = 0; i < board.getBoard().getField()[from].getConnection().length; i++) {
            if (board.getBoard().getField()[from].getConnection()[i] == board.getBoard().getField()[to]) {
                areFieldsConnected = true;
            }
        }

        return isGameRunning() && !isFromFieldEmpty && isToFieldEmpty && correctPiece && areFieldsConnected;
    }

    @Override
    public int[] getPositions() {
        int[] loc = new int[board.getBoard().getSpider().length + 1];
        loc[0] = board.getBoard().getFly().location;
        for (int i = 1; i < board.getBoard().getSpider().length + 1; i++) {
            loc[i] = board.getBoard().getSpider()[i-1].location;
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
        for (int i = 0; i < board.getBoard().getField().length; i++) {
            int numberOfConnections = 6;
            for (int j = 0; j < 6; j++) {
                if (board.getBoard().getField()[i].getConnection()[j] == null) numberOfConnections--;
            }

            ArrayList<Integer> connectionOfField = new ArrayList<>();

            for (int j = 0; j < numberOfConnections; j++) {
                connectionOfField.add(board.getBoard().getField()[i].getConnection()[j].getNumber());
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
            board.getBoard().getField()[to].setPiece(board.getBoard().getField()[from].getPiece());
            board.getBoard().getField()[from].setPiece(Pieces.EMPTY);

            board.getBoard().getFly().location = to;
            isFlysTurn = !isFlysTurn;
        } else {
            System.out.println("Invalid");
        }
        display();
    }

    @Override
    public void moveSpider(int from, int to, Piece p) {
        if (isMoveValid(from, to)) {
            board.getBoard().getField()[to].setPiece(board.getBoard().getField()[from].getPiece());
            board.getBoard().getField()[from].setPiece(Pieces.EMPTY);

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
        for (Piece p: board.getBoard().getSpider()) {
            if (p.location == location) pieceToReturn = p;
        }
        if (board.getBoard().getFly().location == location) pieceToReturn = board.getBoard().getFly();

        return pieceToReturn;
    }

    public boolean isGameRunning() {
        if (
                board.getBoard().getFly().location == 0 ||
                        board.getBoard().getFly().location == 5 ||
                        board.getBoard().getFly().location == 10 ||
                        board.getBoard().getFly().location == 14 ||
                        board.getBoard().getFly().location == 18 ||
                        board.getBoard().getFly().location == 22
        ) {
            isGameRunning = false;
            pieceWon = 1;
        }

        int unavailableFields = 0;
        for (int i = 0; i < board.getBoard().getField()[board.getBoard().getFly().location].getConnection().length; i++) {
            if(
                    board.getBoard().getField()[board.getBoard().getFly().location].getConnection()[i] == null ||
                            board.getBoard().getField()[board.getBoard().getFly().location].getConnection()[i].getPiece() != Pieces.EMPTY
            ) unavailableFields++;
        }

        if (unavailableFields >= board.getBoard().getField()[board.getBoard().getFly().location].getConnection().length) {
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
        int[] spiderLoc = new int[board.getBoard().getSpider().length];

        for (int i = 0; i < board.getBoard().getField().length; i++) {
            if(board.getBoard().getField()[i].getPiece() == Pieces.FLY) {
                System.out.print("F ");
                flyLoc = i;
            } else if(board.getBoard().getField()[i].getPiece() == Pieces.SPIDER) {
                System.out.print("S ");
                spiderLoc[spiderArrayIndex] = i;
                spiderArrayIndex++;
            } else {
                System.out.print("- ");
            }
        }
        System.out.println("\nPiece objects' locations: ");
        System.out.println("Fly location: " + board.getBoard().getFly().location);
        for (int i = 0; i < board.getBoard().getSpider().length; i++) {
            System.out.println("Spider[" + i + "] location: " + board.getBoard().getSpider()[i].location);
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
                board.newBoard();
                isGameRunning = true;
                pieceWon = 0;
                isFlysTurn = true;
                flyStepsDone = 0;
                spiderStepsDone = 0;
                return true;
            }
            case "pvs": {
                this.gameMode = PVS;
                board.newBoard();
                isGameRunning = true;
                pieceWon = 0;
                isFlysTurn = true;
                flyStepsDone = 0;
                spiderStepsDone = 0;
                return true;
            }
            case "pvf": {
                this.gameMode = PVF;
                board.newBoard();
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
