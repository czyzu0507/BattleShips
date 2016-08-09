package io.github.expansionteam.battleships.gui.models;

import javafx.scene.shape.Rectangle;

import java.util.Objects;

public abstract class Field extends Rectangle {

    private static final int FIELD_SIZE = 30;

    private final Position position;

    Field(Position position) {
        super(FIELD_SIZE, FIELD_SIZE);

        this.position = position;
        updateCss();
    }

    public Position getPosition() {
        return position;
    }

    protected abstract void updateCss();

    @Override
    public boolean equals(Object o) {
        if ((o instanceof Field) == false) {
            return false;
        }

        Field other = (Field) o;
        return Objects.equals(this.position, other.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

}
