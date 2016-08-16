package io.github.expansionteam.battleships.common.events;

import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;

import java.util.List;

public abstract class ShipDestroyedEvent {

    private final PositionData position;
    private final List<PositionData> adjacentPositions;
    private final NextTurnData nextTurn;

    public ShipDestroyedEvent(PositionData position, List<PositionData> adjacentPositions, NextTurnData nextTurn) {
        this.position = position;
        this.adjacentPositions = adjacentPositions;
        this.nextTurn = nextTurn;
    }

    public PositionData getPosition() {
        return position;
    }

    public List<PositionData> getAdjacentPositions() {
        return adjacentPositions;
    }

    public NextTurnData getNextTurn() {
        return nextTurn;
    }

}
