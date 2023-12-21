package hu.nye.szakdolgozat.data.model.game;

public class Board {
    private Field[] field;
    Piece fly;
    Piece[] spider;

    /**
     * Generating game board, making connections inside it and placing pieces onto it.
     */
    public Board() {
        field = new Field[28];

        for (int i = 0; i < field.length; i++) {
            Piece temp = new Piece(Pieces.EMPTY, i);
            field[i] = new Field(Pieces.EMPTY);
            field[i].connection = new Field[6];
        }

        spider = new Piece[5];
        spider[0] = new Piece(Pieces.SPIDER, 0);
        spider[1] = new Piece(Pieces.SPIDER, 5);
        spider[2] = new Piece(Pieces.SPIDER, 10);
        spider[3] = new Piece(Pieces.SPIDER, 14);
        spider[4] = new Piece(Pieces.SPIDER, 18);
        fly = new Piece(Pieces.FLY, 27);


        field[27].piece = Pieces.FLY;
        field[0].piece = Pieces.SPIDER;
        field[5].piece = Pieces.SPIDER;
        field[10].piece = Pieces.SPIDER;
        field[14].piece = Pieces.SPIDER;
        field[18].piece = Pieces.SPIDER;


        field[0].connection[0] = field[5];
        field[0].connection[1] = field[22];
        field[0].connection[2] = field[6];
        field[0].connection[3] = field[1];

        field[1].connection[0] = field[7];
        field[1].connection[1] = field[23];
        field[1].connection[2] = field[2];
        field[1].connection[3] = field[0];

        field[2].connection[0] = field[7];
        field[2].connection[1] = field[24];
        field[2].connection[2] = field[3];
        field[2].connection[3] = field[1];

        field[3].connection[0] = field[9];
        field[3].connection[1] = field[25];
        field[3].connection[2] = field[4];
        field[3].connection[3] = field[2];

        field[4].connection[0] = field[9];
        field[4].connection[1] = field[26];
        field[4].connection[2] = field[27];
        field[4].connection[3] = field[3];

        field[5].connection[0] = field[0];
        field[5].connection[1] = field[10];
        field[5].connection[2] = field[6];

        field[6].connection[0] = field[0];
        field[6].connection[1] = field[11];
        field[6].connection[2] = field[7];
        field[6].connection[3] = field[5];

        field[7].connection[0] = field[2];
        field[7].connection[1] = field[1];
        field[7].connection[2] = field[11];
        field[7].connection[3] = field[8];
        field[7].connection[4] = field[6];

        field[8].connection[0] = field[12];
        field[8].connection[1] = field[9];
        field[8].connection[2] = field[7];

        field[9].connection[0] = field[4];
        field[9].connection[1] = field[3];
        field[9].connection[2] = field[13];
        field[9].connection[3] = field[27];
        field[9].connection[4] = field[8];

        field[10].connection[0] = field[5];
        field[10].connection[1] = field[14];
        field[10].connection[2] = field[15];
        field[10].connection[3] = field[11];

        field[11].connection[0] = field[6];
        field[11].connection[1] = field[7];
        field[11].connection[2] = field[15];
        field[11].connection[3] = field[12];
        field[11].connection[4] = field[10];

        field[12].connection[0] = field[8];
        field[12].connection[1] = field[16];
        field[12].connection[2] = field[13];
        field[12].connection[3] = field[11];

        field[13].connection[0] = field[9];
        field[13].connection[1] = field[17];
        field[13].connection[2] = field[27];
        field[13].connection[3] = field[12];

        field[14].connection[0] = field[10];
        field[14].connection[1] = field[18];
        field[14].connection[2] = field[19];
        field[14].connection[3] = field[15];

        field[15].connection[0] = field[10];
        field[15].connection[1] = field[11];
        field[15].connection[2] = field[20];
        field[15].connection[3] = field[16];
        field[15].connection[4] = field[14];

        field[16].connection[0] = field[12];
        field[16].connection[1] = field[20];
        field[16].connection[2] = field[21];
        field[16].connection[3] = field[17];
        field[16].connection[4] = field[15];

        field[17].connection[0] = field[13];
        field[17].connection[1] = field[21];
        field[17].connection[2] = field[27];
        field[17].connection[3] = field[16];

        field[18].connection[0] = field[14];
        field[18].connection[1] = field[22];
        field[18].connection[2] = field[24];
        field[18].connection[3] = field[19];

        field[19].connection[0] = field[14];
        field[19].connection[1] = field[24];
        field[19].connection[2] = field[20];
        field[19].connection[3] = field[18];

        field[20].connection[0] = field[15];
        field[20].connection[1] = field[16];
        field[20].connection[2] = field[25];
        field[20].connection[3] = field[21];
        field[20].connection[4] = field[19];

        field[21].connection[0] = field[16];
        field[21].connection[1] = field[17];
        field[21].connection[2] = field[26];
        field[21].connection[3] = field[27];
        field[21].connection[4] = field[20];

        field[22].connection[0] = field[18];
        field[22].connection[1] = field[0];
        field[22].connection[2] = field[23];

        field[23].connection[0] = field[1];
        field[23].connection[1] = field[24];
        field[23].connection[2] = field[22];

        field[24].connection[0] = field[18];
        field[24].connection[1] = field[19];
        field[24].connection[2] = field[2];
        field[24].connection[3] = field[25];
        field[24].connection[4] = field[23];

        field[25].connection[0] = field[20];
        field[25].connection[1] = field[3];
        field[25].connection[2] = field[26];
        field[25].connection[3] = field[24];

        field[26].connection[0] = field[21];
        field[26].connection[1] = field[4];
        field[26].connection[2] = field[27];
        field[26].connection[3] = field[25];

        field[27].connection[0] = field[4];
        field[27].connection[1] = field[9];
        field[27].connection[2] = field[13];
        field[27].connection[3] = field[17];
        field[27].connection[4] = field[21];
        field[27].connection[5] = field[26];
    }

    public void makeMove(int from, int to) {
        if (whichPiece(from) == fly) makeMoveFly(from, to);
        for (Piece i : spider) {
            if (whichPiece(from) == i) makeMoveSpider(from, to, i);
        }

        hasWinner();
    }

    Piece whichPiece(int location) {
        Piece toReturn = null;
        for (Piece i: spider) {
            if (i.location == location) toReturn = i;
        }
        if (fly.location == location) toReturn = fly;

        return toReturn;
    }

    public void makeMoveFly(int from, int to) {
        if (isMoveValid(from, to)) {
            field[to].piece = field[from].piece;
            field[from].piece = Pieces.EMPTY;

            fly.location = to;
        } else {
            System.out.println("Invalid");
        }
        display();
    }

    public void makeMoveSpider(int from, int to, Piece p) {
        if (isMoveValid(from, to)) {
            field[to].piece = field[from].piece;
            field[from].piece = Pieces.EMPTY;

            p.location = to;
        } else {
            System.out.println("Invalid");
        }
        display();
    }

    public boolean isMoveValid(int from, int to) {
        boolean areFieldsConnected = false;
        boolean isFromFieldEmpty = field[from].piece == Pieces.EMPTY;
        boolean isToFieldEmpty = field[to].piece == Pieces.EMPTY;

        for (int i = 0; i < field[from].connection.length; i++) {
            if (field[from].connection[i] == field[to]) {
                areFieldsConnected = true;
            }
        }

        return !isFromFieldEmpty && isToFieldEmpty && areFieldsConnected;
    }

    public void hasWinner() {
        if (
                fly.location == 0 ||
                        fly.location == 5 ||
                        fly.location == 10 ||
                        fly.location == 14 ||
                        fly.location == 18 ||
                        fly.location == 22
        ) {
            System.out.println("Fly won!");
        }

        int unavailableFields = 0;
        for (int i = 0; i < field[fly.location].connection.length; i++) {
            if(field[fly.location].connection[i] == null) unavailableFields++;
        }

        int availableFields = 6-unavailableFields;
        for (int i = 0; i < field[fly.location].connection.length; i++) {
            if (field[fly.location].connection[i].piece != Pieces.EMPTY) availableFields--;
        }

        if (availableFields <= 0) System.out.println("Spiders won!");
    }


    public void display() {
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
        System.out.println("Fly location: " + flyLoc);
        for (int i = 0; i < spiderLoc.length; i++) {
            System.out.println("Spider[" + i + "] location: " + spiderLoc[i]);
        }
        System.out.println("\n\n");
    }
}
