package io.github.expansionteam.battleships.gui.models;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class Board extends Parent {

    private final Map<Position, Field> fieldsByPosition;

    @FXML
    private VBox board;

    Board(Map<Position, Field> fieldsByPosition) {
        this.fieldsByPosition = fieldsByPosition;
        updateBoard();
    }

    public void placeShip(Ship ship) {
        ship.getFieldsByPosition().forEach((p, f) -> fieldsByPosition.put(p, f));
        updateBoard();
    }

    private void updateBoard() {
        board = new VBox();

        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Field field = fieldsByPosition.get(Position.of(x, y));
                row.getChildren().add(field);
            }
            board.getChildren().removeAll();
            board.getChildren().add(row);
        }

        getChildren().removeAll();
        getChildren().add(board);
    }

}
