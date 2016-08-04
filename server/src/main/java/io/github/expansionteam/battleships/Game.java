package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Board;
import io.github.expansionteam.battleships.engine.Ship;
import org.apache.log4j.Logger;

import java.util.Collection;

import static io.github.expansionteam.battleships.engine.Board.*;

public class Game {

    private static final Logger log = Logger.getLogger(Game.class.getSimpleName());

    private final Board firstPlayerBoard = new BoardBuilder().build();
    private final Board secondPlayerBoard = new BoardBuilder().build();
    private final RandomShipGenerator randomShipGenerator = new RandomShipGenerator();

    private boolean firstPlayer() {
        return Thread.currentThread().getName().contains("Player_1");
    }

    public Collection<Ship> getPlayerShips() {
        if (firstPlayer()) {
            return firstPlayerBoard.getShips();
        }
        return secondPlayerBoard.getShips();
    }

    public void generateRandomShips() {
        if (firstPlayer()) {
            synchronized (randomShipGenerator) {
                randomShipGenerator.generateRandomShips(firstPlayerBoard);
            }
        } else {
            synchronized (randomShipGenerator) {
                randomShipGenerator.generateRandomShips(secondPlayerBoard);
            }
        }
    }

    public void shoot(int x, int y) {
        if (firstPlayer()) {
            firstPlayerBoard.shootField(x, y);
        }
        secondPlayerBoard.shootField(x, y);
    }
}