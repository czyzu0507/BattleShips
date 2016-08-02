package io.github.expansionteam.battleships.gui.models;

import org.apache.log4j.Logger;

public class Position {

    private static final Logger log = Logger.getLogger(Position.class.getSimpleName());
    private final int x;
    private final int y;
    private static final int MAX = 9;
    private static final int MIN = 0;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        if (x < MIN || x > MAX || y < MIN || y > MAX) {
            log.error("Illegal position: [" + x + ", " + y + "]");
            throw new IllegalArgumentException();
        }
        return new Position(x, y);
    }

    public Position nextPositionHorizontally() {
        return Position.of(x + 1, y);
    }

    public Position nextPositionVertically() {
        return Position.of(x, y + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;

        if (x != position.x) {
            return false;
        }
        return y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

}
