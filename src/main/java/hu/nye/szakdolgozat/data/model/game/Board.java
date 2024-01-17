package hu.nye.szakdolgozat.data.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Board {
    private Field[] field;
    private Piece fly;
    private Piece[] spider;
    private Random random = new Random();
    private boolean flysTurn = true;
    private boolean isGameRunning = true;


    public Board() {
        CreateBoard cb = new CreateBoard();
        field = cb.giveField();
        fly = cb.giveFly();
        spider = cb.giveSpiders();
    }



    //---------------- Main methods to use in Controller ----------------

    public void makeMove(int from, int to) {
        if (whichPiece(from) == fly) makeMoveFly(from, to);
        for (Piece i : spider) {
            if (whichPiece(from) == i) makeMoveSpider(from, to, i);
        }
    }

    public boolean makeRandomMoveSpider() {
        Piece randomSpider = spider[random.nextInt(spider.length)];

        int unavailableFields = 0;
        for (int i = 0; i < field[randomSpider.location].connection.length; i++) {
            if(field[randomSpider.location].connection[i] == null) unavailableFields++;
        }
        int availableFields = 6 - unavailableFields;
        int randomConnection = random.nextInt(availableFields);
        int randomField = field[randomSpider.location].connection[randomConnection].number;

        if(isMoveValid(randomSpider.location, randomField)) {
            makeMoveSpider(randomSpider.location, randomField, randomSpider);
            return true;
        } else {
            makeRandomMoveSpider();
        }
        return false;
    }

    public boolean makeRandomMoveFly() {
        Piece randomSpider = spider[random.nextInt(spider.length)];

        int availableFields = field[fly.location].connection.length;
        for (int i = 0; i < availableFields; i++) {
            if(field[fly.location].connection[i] == null) availableFields--;
        }
        int randomConnection = random.nextInt(availableFields);
        int randomField = field[fly.location].connection[randomConnection].number;

        if(isMoveValid(fly.location, randomField)) {
            makeMoveFly(fly.location, randomField);
            return true;
        } else {
            makeRandomMoveFly();
        }
        return false;
    }

    public boolean isMoveValid(int from, int to) {
        boolean correctPiece = false;
        if(flysTurn) {
            if (from == fly.location) {
                correctPiece = true;
            } else return false;
        } else {
            for (int i = 0; i < spider.length; i++) {
                if (from == spider[i].location) correctPiece = true;
            }
        }

        boolean areFieldsConnected = false;
        boolean isFromFieldEmpty = field[from].piece == Pieces.EMPTY;
        boolean isToFieldEmpty = field[to].piece == Pieces.EMPTY;

        for (int i = 0; i < field[from].connection.length; i++) {
            if (field[from].connection[i] == field[to]) {
                areFieldsConnected = true;
            }
        }

        return !isFromFieldEmpty && isToFieldEmpty && correctPiece && areFieldsConnected && isGameRunning();
    }

    public int[] getPositions() {
        int[] loc = new int[spider.length + 1];
        loc[0] = fly.location;
        for (int i = 1; i < spider.length + 1; i++) {
            loc[i] = spider[i-1].location;
        }

        return loc;
    }

    public HashMap<Integer, ArrayList<Integer>> getConnections() {
        HashMap<Integer, ArrayList<Integer>> connections = new HashMap<>();
        for (int i = 0; i < field.length; i++) {
            int numberOfConnections = 6;
            for (int j = 0; j < 6; j++) {
                if (field[i].connection[j] == null) numberOfConnections--;
            }

            ArrayList<Integer> connectionOfField = new ArrayList<>();

            for (int j = 0; j < numberOfConnections; j++) {
                connectionOfField.add(field[i].connection[j].number);
                connections.put(i, connectionOfField);
            }
        }

        for (Map.Entry<Integer, ArrayList<Integer>> entry : connections.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Integer> values = entry.getValue();
            System.out.println("Key: " + key + ", Values: " + values);
        }

        return connections;
    }


    //---------------- Child methods of Main methods listed above ----------------


    private void makeMoveFly(int from, int to) {
        if (isMoveValid(from, to)) {
            field[to].piece = field[from].piece;
            field[from].piece = Pieces.EMPTY;

            fly.location = to;
            flysTurn = ! flysTurn;
        } else {
            System.out.println("Invalid");
        }
        display();
    }

    private void makeMoveSpider(int from, int to, Piece p) {
        if (isMoveValid(from, to)) {
            field[to].piece = field[from].piece;
            field[from].piece = Pieces.EMPTY;

            p.location = to;
            flysTurn = ! flysTurn;
        } else {
            System.out.println("Invalid");
        }
        display();
    }



    private Piece whichPiece(int location) {
        Piece toReturn = null;
        for (Piece i: spider) {
            if (i.location == location) toReturn = i;
        }
        if (fly.location == location) toReturn = fly;

        return toReturn;
    }

    private boolean isGameRunning() {
        if (
                fly.location == 0 ||
                        fly.location == 5 ||
                        fly.location == 10 ||
                        fly.location == 14 ||
                        fly.location == 18 ||
                        fly.location == 22
        ) {
            isGameRunning = false;
            System.out.println("Fly won!");
        }

        int unavailableFields = 0;
        for (int i = 0; i < field[fly.location].connection.length; i++) {
            if(
                    field[fly.location].connection[i] == null ||
                    field[fly.location].connection[i].piece != Pieces.EMPTY
            ) unavailableFields++;
        }

        if (unavailableFields >= field[fly.location].connection.length) {
            isGameRunning = false;
            System.out.println("Spiders won!");
        }
        return isGameRunning;
    }

    public boolean isFlysTurn() {
        return flysTurn;
    }

    private void display() {
        int flyLoc = -1;
        int spiderArrayIndex = 0;
        int[] spiderLoc = new int[spider.length];

        for (int i = 0; i < field.length; i++) {
            if(field[i].piece == Pieces.FLY) {
                System.out.print("F ");
                flyLoc = i;
            } else if(field[i].piece == Pieces.SPIDER) {
                System.out.print("S ");
                spiderLoc[spiderArrayIndex] = i;
                spiderArrayIndex++;
            } else {
                System.out.print("- ");
            }
        }
        System.out.println("\nPiece objects' locations: ");
        System.out.println("Fly location: " + fly.location);
        for (int i = 0; i < spider.length; i++) {
            System.out.println("Spider[" + i + "] location: " + spider[i].location);
        }

        System.out.println("\nBoard's locations: ");
        System.out.println("Fly found on field number " + flyLoc);
        for (int i = 0; i < spiderLoc.length; i++) {
            System.out.println("Spider found on field number " + spiderLoc[i]);
        }
        System.out.println("\n flysTurn: " + flysTurn + "\n");
    }

}