package io.github.expansionteam.battleships.gui.models;

import com.google.common.base.MoreObjects;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public abstract class Field extends Rectangle {

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

    public static class FieldBuilder {

        private enum FieldType {
            OPPONENT, PLAYER;
        }

        private final FieldType fieldType;
        private final Position position;

        private boolean occupied = false;
        private boolean shot = false;

        FieldBuilder(FieldType fieldType, Position position) {
            this.fieldType = fieldType;
            this.position = position;
        }

        public static FieldBuilder opponentField(Position position) {
            return new FieldBuilder(FieldType.OPPONENT, position);
        }

        public static FieldBuilder playerField(Position position) {
            return new FieldBuilder(FieldType.PLAYER, position);
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
            switch (fieldType) {
                case OPPONENT:
                    return new OpponentField(position, occupied, shot);
                case PLAYER:
                    return new PlayerField(position, occupied, shot);
                default:
                    throw new AssertionError();
            }
        }

    }

}
