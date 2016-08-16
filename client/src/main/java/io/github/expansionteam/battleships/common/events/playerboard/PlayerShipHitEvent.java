package io.github.expansionteam.battleships.common.events.playerboard;

import io.github.expansionteam.battleships.common.events.ShipHitEvent;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;

public class PlayerShipHitEvent extends ShipHitEvent {

    public PlayerShipHitEvent(PositionData position, NextTurnData nextTurn) {
        super(position, nextTurn);
    }

}
