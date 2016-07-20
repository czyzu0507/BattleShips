package io.github.expansionteam.battleships.engine;

import java.util.Set;
import java.util.TreeSet;

import static io.github.expansionteam.battleships.engine.Orientation.*;

class Field implements Comparable<Field> {
    // coordinates (in an array/mesh)
    private final int x, y;
    private Ship shipParent = null;     // pointer to the ship that contains this field
    private State state = State.NOT_HIT;

    Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private enum State {
        HIT, NOT_HIT
    }

    Set<Field> createAllPossibleAdjacentFields() {
        Set<Field> set = new TreeSet<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy == 0 && dx == 0)
                    continue;
                set.add(new Field(x + dx, y + dy));
            }
        }
        return set;
    }

    // part of a ship?
    boolean isPartOfTheShip() {
        return shipParent != null;
    }

    // part of a particular ship
    boolean isPartOfTheShip(Ship ship) {
        return shipParent == ship;
    }

    // add pointer
    void setPointerToShip(Ship ship) {
        shipParent = ship;
    }

    // switch field (based on Orientation)
    Field nextField(Orientation orientation) {
        if (orientation == HORIZONTAL)
            return new Field(x + 1, y);
        else
            return new Field(x, y + 1);
    }

    // hit
    void markAsHit() {
        state = State.HIT;
    }

    // is hit?
    boolean isHit() {
        return state == State.HIT;
    }

    // does not allow null values!
    @Override
    public int compareTo(final Field f) {
        int yDiff = y - f.y;
        if (yDiff != 0)
            return yDiff;
        return x - f.x;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    @Override
    public boolean equals(Object fieldObject) {
        if (this == fieldObject) return true;
        if (fieldObject == null || !(getClass().equals(fieldObject.getClass())))
            return false;
        Field field = (Field) fieldObject;
        return x == field.x && y == field.y;
    }

    // TODO: remove this later
    // only for 'visual' test
    @Override
    public String toString() {
        if (shipParent == null) {
            return x + "" + y;
        }
        return "\u001B[32m S\u001B[0m";
    }
}
