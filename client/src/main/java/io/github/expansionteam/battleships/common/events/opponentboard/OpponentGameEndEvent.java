package io.github.expansionteam.battleships.common.events.opponentboard;

import io.github.expansionteam.battleships.common.events.GameEndEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;

import java.util.List;

public class OpponentGameEndEvent extends GameEndEvent {

    public OpponentGameEndEvent(PositionData position, List<PositionData> adjacentPositions) {
        super(position, adjacentPositions);
    }

}
