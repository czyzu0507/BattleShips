package io.github.expansionteam.battleships.common.events.opponentboard;

import io.github.expansionteam.battleships.common.events.EmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;

public class OpponentEmptyFieldHitEvent extends EmptyFieldHitEvent {

    public OpponentEmptyFieldHitEvent(PositionData position, NextTurnData nextTurn) {
        super(position, nextTurn);
    }

}
