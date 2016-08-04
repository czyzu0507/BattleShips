package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Board;
import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Ship;
import org.apache.log4j.Logger;

import java.util.Collection;

import static io.github.expansionteam.battleships.engine.Board.*;

public class Game {

    private static final Logger log = Logger.getLogger(Game.class.getSimpleName());

    private final Board firstPlayerBoard = new BoardBuilder().build();
    private final Board secondPlayerBoard = new BoardBuilder().build();

    public Collection<Ship> getPlayerShips() {
        if (Thread.currentThread().getName().contains("Player_1")) {
            return firstPlayerBoard.getShips();
        }
        return secondPlayerBoard.getShips();
    }

    public void generateRandomShips() {
        RandomShipGenerator rsg = new RandomShipGenerator();
        if (Thread.currentThread().getName().contains("Player_1")) {
            rsg.generateRandomShips(firstPlayerBoard);
        } else {
            rsg.generateRandomShips(secondPlayerBoard);
        }
    }
}