package io.github.expansionteam.battleships.gui.models;

import java.util.Map;

public class PlayerBoard extends Board {

    PlayerBoard(Map<Position, Field> fieldsByPosition) {
        super(fieldsByPosition);
    }

}
