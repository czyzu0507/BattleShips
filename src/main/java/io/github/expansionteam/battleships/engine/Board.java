package io.github.expansionteam.battleships.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Board implements Iterable<Field> {
    private static final int X = 10;    // constraint for x
    private static final int Y = 10;    // constraint for y
    private final Set<Field> board;   // board representation
    private final Set<Ship> ships = new HashSet<>();

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

    // add new Ship - null if it is not possible to append the ship
    // TODO: consider changing return value to boolean
  /*  Ship appendShip(Field startingField, Orientation orientation, int length) {
        // create ship
        Ship tmp = new Ship.ShipBuilder(this, startingField, orientation, length).build();

    }*/

    // generates set for the board (null if coordinates are reached)
    protected Set<Field> generateSetOfFieldsForShip(Field field, Orientation orientation, int length) {
        Set<Field> fields = new HashSet<>();

        while (length > 0) {
            Field fieldFromTheBoard = getFieldFromTheBoard( field );
            if (fieldFromTheBoard == null) {
                return null;
            }
            fields.add(fieldFromTheBoard);
            field = field.nextField( orientation );
            --length;
        }
        return fields;
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
