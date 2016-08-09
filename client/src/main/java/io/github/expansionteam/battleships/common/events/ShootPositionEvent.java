package io.github.expansionteam.battleships.common.events;

import io.github.expansionteam.battleships.common.events.data.PositionData;

public class ShootPositionEvent {

    private final PositionData position;

    public ShootPositionEvent(PositionData position) {
        this.position = position;
    }

    public PositionData getPosition() {
        return position;
    }

}
