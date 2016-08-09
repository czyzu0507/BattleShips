package io.github.expansionteam.battleships.common.events;

import io.github.expansionteam.battleships.common.events.data.PositionData;

public class ShipHitEvent {

    private final PositionData position;

    public ShipHitEvent(PositionData position) {
        this.position = position;
    }

    public PositionData getPosition() {
        return position;
    }

}
