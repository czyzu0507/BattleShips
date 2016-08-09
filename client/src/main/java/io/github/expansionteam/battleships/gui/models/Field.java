package io.github.expansionteam.battleships.gui.models;

import com.google.common.base.MoreObjects;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public abstract class Field extends Rectangle {

    private static final int FIELD_SIZE = 30;

    private final Position position;
    private boolean isOccupied = false;
    private boolean wasShot = false;

    Field(Position position) {
        super(FIELD_SIZE, FIELD_SIZE);

        this.position = position;
        updateCss();
    }

    public Position getPosition() {
        return position;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean wasShot() {
        return wasShot;
    }

    public void shoot() {
        wasShot = true;
        updateCss();
    }

    public void occupy() {
        isOccupied = true;
        updateCss();
    }

    private void updateCss() {
        String cssClass;
        if (isOccupied) {
            if (wasShot) {
                cssClass = "field-was-shot-hit";
            } else {
                cssClass = "field-is-occupied";
            }
        } else {
            if (wasShot) {
                cssClass = "field-was-shot-miss";
            } else {
                cssClass = "field-is-empty";
            }
        }

        getStyleClass().removeAll();
        getStyleClass().add(cssClass);
    }

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
