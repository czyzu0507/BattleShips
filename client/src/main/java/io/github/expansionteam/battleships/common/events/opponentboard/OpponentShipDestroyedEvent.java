package io.github.expansionteam.battleships.common.events.opponentboard;

import io.github.expansionteam.battleships.common.events.ShipDestroyedEvent;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;

import java.util.List;

public class OpponentShipDestroyedEvent extends ShipDestroyedEvent {

    public OpponentShipDestroyedEvent(PositionData position, List<PositionData> adjacentPositions, NextTurnData nextTurn) {
        super(position, adjacentPositions, nextTurn);
    }

}
