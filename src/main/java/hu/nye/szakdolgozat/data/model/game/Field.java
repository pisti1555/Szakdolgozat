package hu.nye.szakdolgozat.data.model.game;

public class Field {
    Field[] connection;
    Pieces piece;
    int number;

    public Field(Pieces piece) {
        this.piece = piece;
    }
}
