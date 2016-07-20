package io.github.expansionteam.battleships.gui.models;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public PlayerBoard createEmptyPlayerBoard() {
        Map<Position, Field> fieldsByPosition = new HashMap<>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                fieldsByPosition.put(Position.of(x, y), Field.FieldBuilder.playerField(Position.of(x, y)).build());
            }
        }

        return new PlayerBoard(fieldsByPosition);
    }

    public OpponentBoard createEmptyOpponentBoard() {
        Map<Position, Field> fieldsByPosition = new HashMap<>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Field field = Field.FieldBuilder.opponentField(Position.of(x, y)).build();

                field.setOnMouseClicked(event -> {
                    Field clickedField = (Field) event.getSource();
                    clickedField.shoot();
                });
                field.setOnMouseEntered(event -> {
                    Field clickedField = (Field) event.getSource();
                    clickedField.getStyleClass().remove("field-is-empty");
                    clickedField.getStyleClass().add("field-shot-hint-legal");
                });
                field.setOnMouseExited(event -> {
                    Field field2 = (Field) event.getSource();
                    field2.getStyleClass().remove("field-shot-hint-legal");
                    field2.getStyleClass().add("field-is-empty");
                });

                fieldsByPosition.put(Position.of(x, y), field);
            }
        }

        return new OpponentBoard(fieldsByPosition);
    }

}
