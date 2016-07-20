package io.github.expansionteam.battleships.gui.models;

import javafx.scene.shape.Rectangle;

public class Field extends Rectangle {

    private static final int FIELD_SIZE = 30;

    private final Position position;
    private boolean occupied;
    private boolean shot;

    private String cssClass;

    Field(Position position, boolean occupied, boolean shot, String cssClass) {
        super(FIELD_SIZE, FIELD_SIZE);

        this.position = position;
        this.occupied = occupied;
        this.shot = shot;
        this.cssClass = cssClass;

        updateCss();
    }

    public static Field createEmpty(Position position) {
        return new Field(position, false, false, "field-is-empty");
    }

    public static Field createOccupied(Position position) {
        return new Field(position, false, false, "field-is-occupied");
    }

    public Position getPosition() {
        return position;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean wasShot() {
        return shot;
    }

    public void shoot() {
        this.cssClass = "field-was-shot-miss";
        updateCss();
    }

    private void updateCss() {
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
            return new Field(position, occupied, shot, "field-is-empty");
        }

    }
}
