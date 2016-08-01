package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Board;
import org.apache.log4j.Logger;


import java.util.Random;

public class Game {

    private static final Logger log = Logger.getLogger(Game.class.getSimpleName());


    public void start() {
       // playerBoard.generateRandomShips();

    }

    public static void main(String[] args) {

        log.info("Logger test in server.");
        Game game = new Game();

       /* Game game = new Game();
        Board.printTmp( game.playerBoard );
        game.start();
        Board.printTmp( game.playerBoard );*/
    }


/*
    // just for now - random ships
    public void generateRandomShips() {
        Random random = new Random();
        int[] shipsArr = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};           // consider using our map - availableShips - iterate over keys !!
        for (int i = 0; i < shipsArr.length; ) {
            Field field = new Field(random.nextInt(10), random.nextInt(10));
            Orientation orientation = resolveOrientation(random.nextInt(2));
            int length = shipsArr[i];
            if (appendShip(field, orientation, length)) {
                i++;
            }
        }
    }

    private Orientation resolveOrientation(int randomInt) {
        if (randomInt % 2 == 0) {
            return Orientation.VERTICAL;
        } else {
            return Orientation.HORIZONTAL;
        }
    }*/
}