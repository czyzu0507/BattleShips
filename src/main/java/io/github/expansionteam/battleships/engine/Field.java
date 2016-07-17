package io.github.expansionteam.battleships.engine;

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

    // part of a ship?
    boolean isPartOfTheShip() {
        return shipParent != null;
    }

    // add pointer
    void setPointerToShip(Ship ship) {
        shipParent = ship;
    }

    // switch field (based on Orientation)
    Field nextField(Orientation orientation) {
        if (orientation == HORIZONTAL)
            return new Field( x+1, y );
        else
            return new Field( x, y+1 );
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
        int xDiff = x - f.x;
        if (xDiff != 0)
            return xDiff;
        return y - f.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    @Override
    public boolean equals(Object fieldObject) {
        if (this == fieldObject) return true;
        if (fieldObject == null || !(getClass().equals( fieldObject.getClass() )))
            return false;
        Field field = (Field) fieldObject;
        return x == field.x && y == field.y;
    }
}
