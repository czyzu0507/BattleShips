package io.github.expansionteam.battleships.gui.models;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Board createEmptyPlayerBoard() {
        Map<Position, Field> fieldsByPosition = new HashMap<>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                fieldsByPosition.put(Position.of(x, y), Field.createEmpty());
            }
        }

        return new Board(fieldsByPosition);
    }

    public Board createEmptyOpponentBoard() {
        Map<Position, Field> fieldsByPosition = new HashMap<>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                fieldsByPosition.put(Position.of(x, y), Field.createEmpty());
            }
        }

        return new Board(fieldsByPosition);
    }

}
