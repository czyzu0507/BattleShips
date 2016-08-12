package io.github.expansionteam.battleships.common.events;

import io.github.expansionteam.battleships.common.events.data.PositionData;

import java.util.List;

public abstract class GameEndEvent {

    private final PositionData position;
    private final List<PositionData> adjacentPositions;

    public GameEndEvent(PositionData position, List<PositionData> adjacentPositions) {
        this.position = position;
        this.adjacentPositions = adjacentPositions;
    }

    public PositionData getPosition() {
        return position;
    }

    public List<PositionData> getAdjacentPositions() {
        return adjacentPositions;
    }

}
