package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Board;
import org.apache.log4j.Logger;

public class Game {
    private final Board playerBoard = new Board();
    private static final Logger log = Logger.getLogger(Game.class.getSimpleName());

    public void start() {
        playerBoard.generateRandomShips();

    }

    public static void main(String[] args) {
        log.info("Logger test in server.");
        Game game = new Game();
        Board.printTmp( game.playerBoard );
        game.start();
        Board.printTmp( game.playerBoard );
    }
}