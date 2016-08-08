package io.github.expansionteam.battleships.gui.models;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class OpponentBoard extends Board {

    OpponentBoard(EventHandler<? super MouseEvent> mouseClickedEventHandler,
                EventHandler<? super MouseEvent> mouseEnteredEventHandler,
                EventHandler<? super MouseEvent> mouseExitedEventHandler,
                Map<Position, Field> fieldsByPosition) {
        super(mouseClickedEventHandler, mouseEnteredEventHandler, mouseExitedEventHandler, fieldsByPosition);
    }

    public void fieldWasShotAndHit(Position position) {
        fieldsByPosition.get(position).occupy();
        fieldsByPosition.get(position).shoot();
    }

    public void fieldWasShotAndMissed(Position position) {
        fieldsByPosition.get(position).shoot();
    }

}
