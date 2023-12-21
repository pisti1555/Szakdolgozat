package hu.nye.szakdolgozat.data.model.game;

public class Field {
    Field[] connection;
    Pieces piece;

    public Field(Pieces piece) {
        this.piece = piece;
    }
}
