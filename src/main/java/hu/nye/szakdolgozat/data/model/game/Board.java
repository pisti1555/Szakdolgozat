package hu.nye.szakdolgozat.data.model.game;

public class Board {
    private final Field[] field;
    private final Piece fly;
    private final Piece[] spider;

    public Board() {
        CreateBoard cb = new CreateBoard();
        field = cb.giveField();
        fly = cb.giveFly();
        spider = cb.giveSpiders();
    }

    public Field[] getField() {
        return field;
    }

    public Piece getFly() {
        return fly;
    }

    public Piece[] getSpider() {
        return spider;
    }
}