package io.github.expansionteam.battleships.gui.models;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class PlayerBoard extends Board {

    PlayerBoard(EventHandler<? super MouseEvent> mouseClickedEventHandler,
          EventHandler<? super MouseEvent> mouseEnteredEventHandler,
          EventHandler<? super MouseEvent> mouseExitedEventHandler,
          Map<Position, Field> fieldsByPosition) {
        super(mouseClickedEventHandler, mouseEnteredEventHandler, mouseExitedEventHandler, fieldsByPosition);
    }

    public void placeShip(Ship ship) {
        ship.getFieldsByPosition().forEach((p, f) -> fieldsByPosition.put(p, f));
        updateBoard();
    }

}
