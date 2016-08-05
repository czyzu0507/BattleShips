package io.github.expansionteam.battleships.common.events;

import io.github.expansionteam.battleships.common.events.data.PositionData;

public class EmptyFieldHitEvent {

    private final PositionData position;

    public EmptyFieldHitEvent(PositionData position) {
        this.position = position;
    }

    public PositionData getPosition() {
        return position;
    }

}
