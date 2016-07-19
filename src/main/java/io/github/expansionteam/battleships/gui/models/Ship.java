package io.github.expansionteam.battleships.gui.models;

import javafx.geometry.Orientation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Ship {

    private final Map<Position, Field> fieldsByPosition;

    Ship(Map<Position, Field> fieldsByPosition) {
        this.fieldsByPosition = fieldsByPosition;
    }

    public static Ship create(Position position, ShipOrientation orientation, ShipSize shipSize) {
        Map<Position, Field> fieldsByPosition = new HashMap<>();

        for (int i = 0; i < shipSize.getValue(); i++) {
            fieldsByPosition.put(position, Field.createOccupied());

            if (orientation.equals(ShipOrientation.HORIZONTAL)) {
                position = position.nextPositionHorizontally();
            } else {
                position = position.nextPositionVertically();
            }
        }

        return new Ship(fieldsByPosition);
    }

    public Map<Position, Field> getFieldsByPosition() {
        return fieldsByPosition;
    }

}
