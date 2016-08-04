package io.github.expansionteam.battleships.gui.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            fieldsByPosition.put(position, Field.FieldBuilder.playerField(position).occupied().build());

            if (i == shipSize.getValue() - 1) {
                break;
            }
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

    @Override
    public boolean equals(Object o) {
        if ((o instanceof Ship) == false) {
            return false;
        }

        Ship other = (Ship) o;
        return Objects.equals(this.fieldsByPosition, other.fieldsByPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldsByPosition);
    }
}
