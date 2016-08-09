package io.github.expansionteam.battleships.gui.models;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public abstract class Board extends Parent {

    private final EventHandler<? super MouseEvent> mouseClickedEventHandler;
    private final EventHandler<? super MouseEvent> mouseEnteredEventHandler;
    private final EventHandler<? super MouseEvent> mouseExitedEventHandler;

    @FXML
    private VBox board;

    Board(EventHandler<? super MouseEvent> mouseClickedEventHandler,
          EventHandler<? super MouseEvent> mouseEnteredEventHandler,
          EventHandler<? super MouseEvent> mouseExitedEventHandler) {
        this.mouseClickedEventHandler = mouseClickedEventHandler;
        this.mouseEnteredEventHandler = mouseEnteredEventHandler;
        this.mouseExitedEventHandler = mouseExitedEventHandler;
    }

    protected abstract Map<Position, ? extends Field> getFieldsByPosition();

    protected void updateBoard() {
        board = new VBox();

        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Field field = getFieldsByPosition().get(Position.of(x, y));
                field.setOnMouseClicked(mouseClickedEventHandler);
                field.setOnMouseEntered(mouseEnteredEventHandler);
                field.setOnMouseExited(mouseExitedEventHandler);

                row.getChildren().add(field);
            }
            board.getChildren().add(row);
        }

        getChildren().add(board);
    }

}
