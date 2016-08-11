package io.github.expansionteam.battleships.common.events;

import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.ShipData;

import java.util.List;

public class ShipsGeneratedEvent {

    private final List<ShipData> ships;
    private final NextTurnData nextTurn;

    public ShipsGeneratedEvent(List<ShipData> ships, NextTurnData nextTurn) {
        this.ships = ships;
        this.nextTurn = nextTurn;
    }

    public List<ShipData> getShips() {
        return ships;
    }

    public NextTurnData getNextTurn() {
        return nextTurn;
    }

}
