package io.github.expansionteam.battleships.common.events.playerboard;

import io.github.expansionteam.battleships.common.events.GameEndEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;

import java.util.List;

public class PlayerGameEndEvent extends GameEndEvent {

    public PlayerGameEndEvent(PositionData position, List<PositionData> adjacentPositions) {
        super(position, adjacentPositions);
    }

}
