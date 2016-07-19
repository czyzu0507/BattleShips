package io.github.expansionteam.battleships.gui.models;

import javafx.scene.shape.Rectangle;

public class Field extends Rectangle {

    private static final int FIELD_SIZE = 30;

    Field(String cssClass) {
        super(FIELD_SIZE, FIELD_SIZE);
        getStyleClass().add(cssClass);
    }

    public static Field createEmpty() {
        return new Field("field-empty");
    }

    public static Field createOccupied() {
        return new Field("field-occupied");
    }

}
