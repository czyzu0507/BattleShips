package io.github.expansionteam.battleships.gui.models;

import com.google.common.base.MoreObjects;

import java.util.*;

public class Ship {

    private final Position position;
    private final ShipSize size;
    private final ShipOrientation orientation;
    private final Set<Position> positions;

    Ship(Position position, ShipSize size, ShipOrientation orientation, Set<Position> positions) {
        this.position = position;
        this.size = size;
        this.orientation = orientation;
        this.positions = positions;
    }

    public static Ship createHorizontal(Position position, ShipSize shipSize) {
        return create(position, shipSize, ShipOrientation.HORIZONTAL);
    }

    public static Ship createVertical(Position position, ShipSize shipSize) {
        return create(position, shipSize, ShipOrientation.VERTICAL);
    }

    private static Ship create(Position position, ShipSize size, ShipOrientation orientation) {
        Set<Position> positions = new HashSet<>();

        Position oldPosition = position;

        for (int i = 0; i < size.getValue(); i++) {
            positions.add(position);

            if (i == size.getValue() - 1) {
                break;
            }
            if (orientation.equals(ShipOrientation.HORIZONTAL)) {
                position = position.nextPositionHorizontally();
            } else {
                position = position.nextPositionVertically();
            }
        }

        return new Ship(oldPosition, size, orientation, positions);
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

    public Set<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if ((o instanceof Ship) == false) {
            return false;
        }

        Ship other = (Ship) o;
        return Objects.equals(this.positions, other.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions);
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
