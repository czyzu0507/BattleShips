package io.github.expansionteam.battleships.gui.models;

import javafx.scene.shape.Rectangle;

public class Field extends Rectangle {

    private static final int FIELD_SIZE = 30;

    private final Position position;

    private String cssClass;

    Field(Position position, String cssClass) {
        super(FIELD_SIZE, FIELD_SIZE);

        this.position = position;
        this.cssClass = cssClass;

        updateCss();
    }

    public static Field createEmpty(Position position) {
        return new Field(position, "field-is-empty");
    }

    public static Field createOccupied(Position position) {
        return new Field(position, "field-is-occupied");
    }

    public Position getPosition() {
        return position;
    }

    public void shoot() {
        this.cssClass = "field-was-shot-miss";
        updateCss();
    }

    private void updateCss() {
        getStyleClass().add(cssClass);
    }

}
