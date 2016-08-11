package io.github.expansionteam.battleships.gui.models;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class PlayerBoard extends Board {

    private final Map<Position, PlayerField> fieldsByPosition;

    PlayerBoard(EventHandler<? super MouseEvent> mouseClickedEventHandler,
          EventHandler<? super MouseEvent> mouseEnteredEventHandler,
          EventHandler<? super MouseEvent> mouseExitedEventHandler,
          Map<Position, PlayerField> fieldsByPosition) {
        super(mouseClickedEventHandler, mouseEnteredEventHandler, mouseExitedEventHandler);
        this.fieldsByPosition = fieldsByPosition;
        updateBoard();
    }

    public void placeShip(Ship ship) {
        ship.getPositions().forEach(p -> getFieldsByPosition().get(p).occupy());
    }

    public void positionWasShot(Position position) {
        getFieldsByPosition().get(position).shoot();
    }

    @Override
    protected Map<Position, PlayerField> getFieldsByPosition() {
        return fieldsByPosition;
    }

}
