package io.github.expansionteam.battleships.gui.models;

import java.util.HashMap;
import java.util.Map;

public class Ship {

    private final Map<Position, Field> fieldsByPosition;

    Ship(Map<Position, Field> fieldsByPosition) {
        this.fieldsByPosition = fieldsByPosition;
    }

    public static Ship createHorizontal(Position position, ShipSize shipSize) {
        return create(ShipOrientation.HORIZONTAL, position, shipSize);
    }

    public static Ship createVertical(Position position, ShipSize shipSize) {
        return create(ShipOrientation.VERTICAL, position, shipSize);
    }

    private static Ship create(ShipOrientation orientation, Position position, ShipSize shipSize) {
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
