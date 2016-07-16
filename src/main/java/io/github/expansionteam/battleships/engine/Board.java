package io.github.expansionteam.battleships.engine;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Board implements Iterable<Field> {
    private static final int X = 10;    // constraint for x
    private static final int Y = 10;    // constraint for y
    private final Set<Field> board;   // board representation
    private final Set<Ship> ships = new TreeSet<>();

    Board() {
        board = initializeSet();
    }

    private static Set<Field> initializeSet() {
        Set<Field> initialSet = new TreeSet<>();
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                initialSet.add( new Field(i, j) );
            }
        }
        return initialSet;
    }

    // returns field from the board - to return pointer to the field on the board
    Field getFieldFromTheBoard(Field field) {
        for (Field f : board) {
            if ( f.equals( field ) ) {
                return f;
            }
        }
        return null;
    }

    // allows to iterate over Fields
    @Override
    public Iterator<Field> iterator() {
        return board.iterator();
    }
}
