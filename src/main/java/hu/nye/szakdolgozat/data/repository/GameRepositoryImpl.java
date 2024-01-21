package hu.nye.szakdolgozat.data.repository;

import hu.nye.szakdolgozat.data.model.game.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl implements GameRepository {
    private Board board = null;

    @Autowired
    public GameRepositoryImpl() {
        newBoard();
    }

    @Override
    public void newBoard() {
        this.board = new Board();
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public void setBoard(Board b) {
        this.board = b;
    }
}
