package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Board;
import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Ship;
import org.apache.log4j.Logger;

import java.util.Collection;

import static io.github.expansionteam.battleships.engine.Board.RandomShipGenerator;

class Game {

//    private static final Logger log = Logger.getLogger(Game.class);

    private final Board firstPlayerBoard;
    private final Board secondPlayerBoard;
    private final RandomShipGenerator randomShipGenerator = new RandomShipGenerator();

    static Game createGame() {
        return new Game(new Board.BoardBuilder().build(), new Board.BoardBuilder().build());
    }

    Game(Board firstPlayerBoard, Board secondPlayerBoard) {
        this.firstPlayerBoard = firstPlayerBoard;
        this.secondPlayerBoard = secondPlayerBoard;
    }

    Collection<Ship> getPlayerShips() {
        Board board = currentBoard();
        return board.getShips();
    }

    void generateRandomShips() {
        Board board = currentBoard();
        synchronized (randomShipGenerator) {
            randomShipGenerator.generateRandomShips(board);
        }
    }

    boolean shootOpponentField(int x, int y) {
        return opponentBoard().shootField(x, y);
    }

    boolean isOpponentShipDestroyed(int x, int y) {
        return opponentBoard().isDestroyedShip(x, y);
    }

    boolean isOpponentShipHit(int x, int y) {
        return opponentBoard().isShipField(x, y);
    }

    Collection<Field> getAdjacentToOpponentShip(int x, int y) {
        return opponentBoard().getAdjacentToShip(x, y);
    }

    boolean isEnded() {
        return opponentBoard().areAllShipsDestroyed();
    }

    boolean generatingShipsFinished() {
        return firstPlayerBoard.placingShipsFinished() && secondPlayerBoard.placingShipsFinished();
    }

    private Board currentBoard() {
        return firstPlayer() ? firstPlayerBoard : secondPlayerBoard;
    }

    private Board opponentBoard() {
        return firstPlayer() ? secondPlayerBoard : firstPlayerBoard;
    }

    private boolean firstPlayer() {
        return Thread.currentThread().getName().contains("Player_1");
    }

}