package io.github.expansionteam.battleships.common.events.opponentboard;

import io.github.expansionteam.battleships.common.events.ShipHitEvent;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;

public class OpponentShipHitEvent extends ShipHitEvent {

    public OpponentShipHitEvent(PositionData position, NextTurnData nextTurn) {
        super(position, nextTurn);
    }

}
