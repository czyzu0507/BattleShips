package io.github.expansionteam.battleships.gui.models;

import java.util.Map;

public class OpponentBoard extends Board {

    OpponentBoard(Map<Position, Field> fieldsByPosition) {
        super(fieldsByPosition);
    }

    public void fieldWasShotAndHit(Position position) {
        fieldsByPosition.put(position, Field.FieldBuilder.opponentField(position).occupied().shot().build());
        updateBoard();
    }

    public void fieldWasShotAndMissed(Position position) {
        fieldsByPosition.put(position, Field.FieldBuilder.opponentField(position).shot().build());
        updateBoard();
    }

}
