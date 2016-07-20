package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Board;

public class Game {
    private final Board playerBoard = new Board();

    public void start() {
        playerBoard.generateRandomShips();

    }

    public static void main(String[] args) {
        Game game = new Game();
        Board.printTmp( game.playerBoard );
        game.start();
        Board.printTmp( game.playerBoard );
    }
}