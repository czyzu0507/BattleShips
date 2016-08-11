package io.github.expansionteam.battleships.common.events;

import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;

public abstract class EmptyFieldHitEvent {

    private final PositionData position;
    private final NextTurnData nextTurn;

    public EmptyFieldHitEvent(PositionData position, NextTurnData nextTurn) {
        this.position = position;
        this.nextTurn = nextTurn;
    }

    public PositionData getPosition() {
        return position;
    }

    public NextTurnData getNextTurn() {
        return nextTurn;
    }

}
