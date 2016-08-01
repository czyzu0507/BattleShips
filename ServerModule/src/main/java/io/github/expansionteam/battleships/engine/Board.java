package io.github.expansionteam.battleships.engine;

import java.util.*;
import java.util.stream.Collectors;

public class Board implements Iterable<Field> {
    private static final int X = 10;    // constraint for x
    private static final int Y = 10;    // constraint for y
    private final Set<Field> board;     // board representation
    private final Map<Integer, Integer> availableShips;
    private final Set<Ship> ships = new HashSet<>();

    private Board(BoardBuilder bb) {
        board = bb.board;
        availableShips = bb.availableShips;
    }

    // 'pseudo' - builder
    public static class BoardBuilder {
        private final Set<Field> board = initializeSet();
        private final Map<Integer, Integer> availableShips = initializeMap();

        private static Set<Field> initializeSet() {
            Set<Field> initialSet = new TreeSet<>();
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    initialSet.add(new Field(i, j));
                }
            }
            return initialSet;
        }

        private static Map<Integer, Integer> initializeMap() {
            Map<Integer, Integer> initialMap = new HashMap<>();
            initialMap.put(4, 1);
            initialMap.put(3, 2);
            initialMap.put(2, 3);
            initialMap.put(1, 4);
            return initialMap;
        }

        public Board build() {
            return new Board(this);
        }
    }

    public boolean appendShip(Field startingField, Orientation orientation, int length) {
        // checks if ship length is available
        if (!ShipAdderHelper.isLengthAvailable(this, length)) {
            return false;
        }
        Set<Field> setOfShipFields = ShipAdderHelper.generateSetOfFieldsForShip(this, startingField, orientation, length);
        if (setOfShipFields == null) {      // checks if the set is out of bounds
            return false;
        }
        // validate set of current ship-fields
        if (!ShipAdderHelper.validateSet(setOfShipFields))
            return false;
        // validate adjacent fields
        Set<Field> adjacent = Ship.generateSetOfAdjacentFields(this, setOfShipFields);
        for (Field f : adjacent) {
            if (f.getParentShip() != null)
                return false;
        }
        Ship ship = new Ship.ShipBuilder(setOfShipFields).build();
        ShipAdderHelper.decreaseShipCounter(this, length);
        ships.add(ship);
        return true;
    }

    public boolean shootField(Field field) {
        Field boardField = getFieldFromTheBoard(field);
        if (boardField.isHit()) {
            throw new IllegalStateException("Field already shot");
        }
        boardField.markAsHit();
        Ship ship = boardField.getParentShip();
        if (ship == null || !ship.isDestroyed()) {
            return true;
        }
        markAdjacentFieldsAsHit(ship);
        return true;
    }

    // when the ship is destroyed
    private void markAdjacentFieldsAsHit(Ship hitShip) {
        Set<Field> fields = Ship.generateSetOfAdjacentFields(this, hitShip.occupiedFields);
        fields.forEach(Field::markAsHit);
    }

    // returns field from the board - to return pointer to the field on the board
    Field getFieldFromTheBoard(Field field) {
        for (Field f : board) {
            if (f.equals(field)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public Iterator<Field> iterator() {
        return board.iterator();
    }

    static class ShipAdderHelper {
        // checks intersection of sets (with set of fields of other ships)
        static boolean validateSet(Set<Field> set) {
            Set<Field> intersectedFields = set.stream().filter(e -> e.getParentShip() != null).collect(Collectors.toSet());
            return intersectedFields.size() == 0;
        }

        // checks if the type of ship with given length is still available
        static boolean isLengthAvailable(Board board, int length) {
            if (!board.availableShips.containsKey(length)) {
                throw new IllegalStateException("No such length");
            }
            return board.availableShips.get(length) > 0;
        }

        // lowers number of available ship type with given length
        static void decreaseShipCounter(Board board, int length) {
            if (!board.availableShips.containsKey(length)) {
                throw new IllegalStateException("No such length");
            }
            Integer counter = board.availableShips.get(length);
            if (counter < 1) {
                throw new IllegalStateException("No available length");
            }
            board.availableShips.put(length, --counter);
        }

        // generates set for the board (null if coordinates are reached)
        static Set<Field> generateSetOfFieldsForShip(Board board, Field field, Orientation orientation, int length) {
            Set<Field> fields = new HashSet<>();

            while (length > 0) {
                Field fieldFromTheBoard = board.getFieldFromTheBoard(field);
                if (fieldFromTheBoard == null) {
                    return null;
                }
                fields.add(fieldFromTheBoard);
                field = Field.FieldSetGenerator.nextField(field, orientation);
                --length;
            }
            return fields;
        }
    }


    // PRINT HELPER - TO SEE WHAT HAPPENS ON THE BOARD :)
    // TODO: remove this later
    public static void printTmp(Board board) {
        Iterator<Field> iter = board.iterator();
        int n = 0;
        while (iter.hasNext()) {
            System.out.print(iter.next() + "  ");
            ++n;
            if (n % 10 == 0)
                System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}