package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Board;
import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Ship;
import org.apache.log4j.Logger;

import java.util.Collection;

import static io.github.expansionteam.battleships.engine.Board.BoardBuilder;
import static io.github.expansionteam.battleships.engine.Board.RandomShipGenerator;

public class Game {

    private static final Logger log = Logger.getLogger(Game.class);

    private final Board firstPlayerBoard = new BoardBuilder().build();
    private final Board secondPlayerBoard = new BoardBuilder().build();
    private final RandomShipGenerator randomShipGenerator = new RandomShipGenerator();

    public Collection<Ship> getPlayerShips() {
        Board board = currentBoard();
        return board.getShips();
    }

    public void generateRandomShips() {
        Board board = currentBoard();
        synchronized (randomShipGenerator) {
            randomShipGenerator.generateRandomShips(board);
        }
    }

    public boolean shoot(int x, int y) {
        return currentBoard().shootField(x, y);
    }

    public boolean isDestroyedShip(int x, int y) {
        Board board = currentBoard();
        return board.isDestroyedShip(x, y);
    }

    public boolean isShipHit(int x, int y) {
        return isShipField(currentBoard(), x, y);
    }

    public Collection<Field> getAdjacentToShip(int x, int y) {
        Board board = currentBoard();
        return board.getAdjacentToShip(x, y);
    }

    private Board currentBoard() {
        return firstPlayer() ? firstPlayerBoard : secondPlayerBoard;
    }

    private boolean firstPlayer() {
        return Thread.currentThread().getName().contains("Player_1");
    }

    private boolean isShipField(Board board, int x, int y) {
        return board.getFieldFromTheBoard(x, y).isShip();
    }
}