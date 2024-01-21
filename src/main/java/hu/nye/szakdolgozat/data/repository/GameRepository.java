package hu.nye.szakdolgozat.data.repository;

import hu.nye.szakdolgozat.data.model.game.Board;

public interface GameRepository {
    void newBoard();
    Board getBoard();
    void setBoard(Board b);
}
