package io.github.expansionteam.battleships.gui.models;

import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ship {

    private final Position position;
    private final ShipSize size;
    private final ShipOrientation orientation;
    private final Map<Position, Field> fieldsByPosition;

    Ship(Position position, ShipSize size, ShipOrientation orientation, Map<Position, Field> fieldsByPosition) {
        this.position = position;
        this.size = size;
        this.orientation = orientation;
        this.fieldsByPosition = fieldsByPosition;
    }

    public static Ship createHorizontal(Position position, ShipSize shipSize) {
        return create(position, shipSize, ShipOrientation.HORIZONTAL);
    }

    public static Ship createVertical(Position position, ShipSize shipSize) {
        return create(position, shipSize, ShipOrientation.VERTICAL);
    }

    private static Ship create(Position position, ShipSize size, ShipOrientation orientation) {
        Map<Position, Field> fieldsByPosition = new HashMap<>();

        Position oldPosition = position;

        for (int i = 0; i < size.getValue(); i++) {
            fieldsByPosition.put(position, Field.FieldBuilder.playerField(position).occupied().build());

            if (i == size.getValue() - 1) {
                break;
            }
            if (orientation.equals(ShipOrientation.HORIZONTAL)) {
                position = position.nextPositionHorizontally();
            } else {
                position = position.nextPositionVertically();
            }
        }

        return new Ship(oldPosition, size, orientation, fieldsByPosition);
    }

    public Position getPosition() {
        return position;
    }

    public ShipSize getSize() {
        return size;
    }

    public ShipOrientation getOrientation() {
        return orientation;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Ship.class)
                .add("position", position)
                .add("size", size)
                .add("orientation", orientation)
                .toString();
    }

}
