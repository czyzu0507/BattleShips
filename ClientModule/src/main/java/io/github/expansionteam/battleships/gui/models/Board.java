package io.github.expansionteam.battleships.gui.models;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class Board extends Parent {

    private final VBox column = new VBox();

    public Board() {
        for (int i = 0; i < 10; i++) {
            HBox row = new HBox();
            for (int j = 0; j < 10; j++) {
                Rectangle rectangle = new Rectangle(30, 30);
                rectangle.getStyleClass().add("field");
                String text = "Jesteś pewny, że chcesz kliknąć [" + i + ", " + j + "]?";
                rectangle.setOnMouseClicked(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Czy chcesz kliknąć?");
                    alert.setContentText(text);
                    alert.showAndWait();
                });
                rectangle.setOnMouseEntered(event -> {
                    Rectangle currentRectangle = (Rectangle) event.getSource();
                    currentRectangle.getStyleClass().remove("field");
                    currentRectangle.getStyleClass().add("field-highlight");
                });
                rectangle.setOnMouseExited(event -> {
                    Rectangle currentRectangle = (Rectangle) event.getSource();
                    currentRectangle.getStyleClass().remove("field-highlight");
                    currentRectangle.getStyleClass().add("field");
                });

                row.getChildren().add(rectangle);
            }
            column.getChildren().add(row);
        }
        getChildren().add(column);
    }

}
