package io.github.expansionteam.battleships.gui.models;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public abstract class Board extends Parent {

    protected final Map<Position, Field> fieldsByPosition;

    @FXML
    private VBox board;

    Board(Map<Position, Field> fieldsByPosition) {
        this.fieldsByPosition = fieldsByPosition;
        updateBoard();
    }

    protected void updateBoard() {
        board = new VBox();

        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Field field = fieldsByPosition.get(Position.of(x, y));
                row.getChildren().add(field);
            }
            board.getChildren().add(row);
        }

//        getChildren().removeAll();
        getChildren().add(board);
    }

}
