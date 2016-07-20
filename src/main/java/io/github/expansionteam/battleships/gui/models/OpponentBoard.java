package io.github.expansionteam.battleships.gui.models;

import io.github.expansionteam.battleships.gui.models.Field;
import io.github.expansionteam.battleships.gui.models.Position;

import java.util.Map;

public class OpponentBoard extends Board {

    OpponentBoard(Map<Position, Field> fieldsByPosition) {
        super(fieldsByPosition);
    }

}
