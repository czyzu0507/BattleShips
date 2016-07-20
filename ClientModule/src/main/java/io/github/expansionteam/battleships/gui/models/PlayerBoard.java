package io.github.expansionteam.battleships.gui.models;

import java.util.Map;

public class PlayerBoard extends Board {

    PlayerBoard(Map<Position, Field> fieldsByPosition) {
        super(fieldsByPosition);
    }

    public void placeShip(Ship ship) {
        ship.getFieldsByPosition().forEach((p, f) -> fieldsByPosition.put(p, f));
        updateBoard();
    }

}
