package io.github.expansionteam.battleships.gui.models;

import com.google.inject.Inject;
import io.github.expansionteam.battleships.gui.mouseeventhandlers.MouseClickedEventHandler;
import io.github.expansionteam.battleships.gui.mouseeventhandlers.MouseEnteredEventHandler;
import io.github.expansionteam.battleships.gui.mouseeventhandlers.MouseExitedEventHandler;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    private final MouseClickedEventHandler mouseClickedEventHandler;
    private final MouseEnteredEventHandler mouseEnteredEventHandler;
    private final MouseExitedEventHandler mouseExitedEventHandler;

    @Inject
    public BoardFactory(
            MouseClickedEventHandler mouseClickedEventHandler,
            MouseEnteredEventHandler mouseEnteredEventHandler,
            MouseExitedEventHandler mouseExitedEventHandler) {
        this.mouseClickedEventHandler = mouseClickedEventHandler;
        this.mouseEnteredEventHandler = mouseEnteredEventHandler;
        this.mouseExitedEventHandler = mouseExitedEventHandler;
    }

    public PlayerBoard createEmptyPlayerBoard() {
        Map<Position, Field> fieldsByPosition = new HashMap<>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Position position = Position.of(x, y);
                fieldsByPosition.put(position, new PlayerField(position));
            }
        }

        return new PlayerBoard(
                mouseClickedEventHandler::handlePlayerBoard,
                mouseEnteredEventHandler::handlePlayerBoard,
                mouseExitedEventHandler::handlePlayerBoard,
                fieldsByPosition);
    }

    public OpponentBoard createEmptyOpponentBoard() {
        Map<Position, Field> fieldsByPosition = new HashMap<>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Position position = Position.of(x, y);
                fieldsByPosition.put(position, new OpponentField(position));
            }
        }

        return new OpponentBoard(
                mouseClickedEventHandler::handleOpponentBoard,
                mouseEnteredEventHandler::handleOpponentBoard,
                mouseExitedEventHandler::handleOpponentBoard,
                fieldsByPosition);
    }

}
