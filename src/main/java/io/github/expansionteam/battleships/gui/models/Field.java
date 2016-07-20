package io.github.expansionteam.battleships.gui.models;

import javafx.scene.shape.Rectangle;

public class Field extends Rectangle {

    private static final int FIELD_SIZE = 30;

    private final Position position;
    private boolean isOccupied;
    private boolean wasShot;

    Field(Position position, boolean isOccupied, boolean wasShot) {
        super(FIELD_SIZE, FIELD_SIZE);

        this.position = position;
        this.isOccupied = isOccupied;
        this.wasShot = wasShot;

        updateCss();
    }

    public static Field createEmpty(Position position) {
        return new Field(position, false, false);
    }

    public static Field createOccupied(Position position) {
        return new Field(position, true, false);
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

    public static class FieldBuilder {

        private final Position position;

        private boolean occupied = false;
        private boolean shot = false;

        public FieldBuilder(Position position) {
            this.position = position;
        }

        public FieldBuilder occupied() {
            this.occupied = true;
            return this;
        }

        public FieldBuilder shot() {
            this.shot = true;
            return this;
        }

        public Field build() {
            return new Field(position, occupied, shot);
        }

    }

}
