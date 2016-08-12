package io.github.expansionteam.battleships.common.events.playerboard;

import io.github.expansionteam.battleships.common.events.ShipDestroyedEvent;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;

import java.util.List;

public class PlayerShipDestroyedEvent extends ShipDestroyedEvent {

    public PlayerShipDestroyedEvent(PositionData position, List<PositionData> adjacentPositions, NextTurnData nextTurn) {
        super(position, adjacentPositions, nextTurn);
    }

}
