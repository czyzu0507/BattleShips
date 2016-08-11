package io.github.expansionteam.battleships.gui.models;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class OpponentBoard extends Board {

    private final Map<Position, OpponentField> fieldsByPosition;

    OpponentBoard(EventHandler<? super MouseEvent> mouseClickedEventHandler,
                  EventHandler<? super MouseEvent> mouseEnteredEventHandler,
                  EventHandler<? super MouseEvent> mouseExitedEventHandler,
                  Map<Position, OpponentField> fieldsByPosition) {
        super(mouseClickedEventHandler, mouseEnteredEventHandler, mouseExitedEventHandler);
        this.fieldsByPosition = fieldsByPosition;
        updateBoard();
    }

    public void positionWasShotAndHit(Position position) {
        fieldsByPosition.get(position).shoot(true);
    }

    public void positionWasShotAndMissed(Position position) {
        fieldsByPosition.get(position).shoot(false);
    }

    @Override
    protected Map<Position, OpponentField> getFieldsByPosition() {
        return fieldsByPosition;
    }

}
