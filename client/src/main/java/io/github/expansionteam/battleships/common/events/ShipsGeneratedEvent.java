package io.github.expansionteam.battleships.common.events;

import io.github.expansionteam.battleships.common.events.data.ShipData;

import java.util.List;

public class ShipsGeneratedEvent {

    private final List<ShipData> ships;

    public ShipsGeneratedEvent(List<ShipData> ships) {
        this.ships = ships;
    }

    public List<ShipData> getShips() {
        return ships;
    }

}
