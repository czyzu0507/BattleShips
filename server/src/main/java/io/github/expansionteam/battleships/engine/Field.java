package io.github.expansionteam.battleships.engine;

import java.util.Set;
import java.util.TreeSet;

import static io.github.expansionteam.battleships.engine.Field.State.HIT;
import static io.github.expansionteam.battleships.engine.Field.State.NOT_HIT;
import static io.github.expansionteam.battleships.engine.Orientation.HORIZONTAL;

public final class Field implements Comparable<Field> {
    // coordinates (in an array/mesh)
    private final int x, y;
    private Ship shipParent = null;     // pointer to the ship that contains this field
    private State state = NOT_HIT;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Ship getParentShip() {
        return shipParent;
    }

    void setParentShip(Ship ship) {
        shipParent = ship;
    }

    void markAsHit() {
        state = HIT;
    }

    boolean isHit() {
        return state == HIT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    enum State {
        HIT, NOT_HIT
    }

    // helper class
    static class FieldSetGenerator {
        static Set<Field> createAllPossibleAdjacentFields(Field field) {
            Set<Field> set = new TreeSet<>();
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if (dy == 0 && dx == 0)
                        continue;
                    set.add(new Field(field.x + dx, field.y + dy));
                }
            }
            return set;
        }

        static Field nextField(Field field, Orientation orientation) {
            if (orientation == HORIZONTAL)
                return new Field(field.x + 1, field.y);
            else
                return new Field(field.x, field.y + 1);
        }
    }

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
            if (isHit()) {
                return "\u001B[31m " + x + "" + y + "\u001B[0m";
            }
            return x + "" + y;
        }
        if (isHit())
            return "\u001B[31m S\u001B[0m";
        return "\u001B[32m S\u001B[0m";
    }
}