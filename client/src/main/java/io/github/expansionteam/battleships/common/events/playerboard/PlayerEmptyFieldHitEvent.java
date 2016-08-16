package io.github.expansionteam.battleships.common.events.playerboard;

import io.github.expansionteam.battleships.common.events.EmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;

public class PlayerEmptyFieldHitEvent extends EmptyFieldHitEvent {

    public PlayerEmptyFieldHitEvent(PositionData position, NextTurnData nextTurn) {
        super(position, nextTurn);
    }

}
