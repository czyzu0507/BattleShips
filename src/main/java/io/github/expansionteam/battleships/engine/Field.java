package io.github.expansionteam.battleships.engine;

import java.util.Comparator;

public class Field implements Comparator<Field> {
    // coordinates (in an array/mesh)
    final int x;
    final int y;

    Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // does not allow null values!
    @Override
    public int compare(final Field f1, final Field f2) {
        int xDiff = f1.x - f2.x;
        if (xDiff != 0)
            return xDiff;
        return f1.y - f2.y;
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
