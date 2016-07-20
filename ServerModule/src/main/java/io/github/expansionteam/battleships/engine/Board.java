package io.github.expansionteam.battleships.engine;

import java.util.*;
import java.util.stream.Collectors;

public class Board implements Iterable<Field> {
    private static final int X = 10;    // constraint for x
    private static final int Y = 10;    // constraint for y
    private final Set<Field> board;   // board representation
    private final Map<Integer, Integer> availableShips;
    private final Set<Ship> ships = new HashSet<>();

    public Board() {
        board = initializeSet();
        availableShips = initializeMap();
    }


    // just for now - random ships
    public void generateRandomShips() {
        Random random = new Random();
        int[] shipsArr = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};           // consider using our map - availableShips - iterate over keys !!
        for (int i = 0; i < shipsArr.length; ) {
            Field field = new Field(random.nextInt(10), random.nextInt(10));
            Orientation orientation = resolveOrientation(random.nextInt(2));
            int length = shipsArr[i];
            if (appendShip(field, orientation, length)) {
                i++;
            }
        }
    }

    private Orientation resolveOrientation(int randomInt) {
        if (randomInt % 2 == 0) {
            return Orientation.VERTICAL;
        } else {
            return Orientation.HORIZONTAL;
        }
    }


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


    // consider moving adjacent fields to ship (as a set of adjacent fields)
    // TODO: consider name changing
    public boolean appendShip(Field startingField, Orientation orientation, int length) {
        // checks if ship length is available
        if (!isLengthAvailable(length)) {
            return false;
        }
        Set<Field> setOfShipFields = generateSetOfFieldsForShip(startingField, orientation, length);
        if (setOfShipFields == null) {      // checks if the set is out of bounds
            return false;
        }
        // validate set of current ship-fields
        if (!validateSet(setOfShipFields))
            return false;
        // validate adjacent fields
        Set<Field> adjacent = Ship.generateSetOfAdjacentFields(this, setOfShipFields);
        for (Field f : adjacent) {
            if (f.isPartOfTheShip())
                return false;
        }
        Ship ship = new Ship.ShipBuilder(setOfShipFields).build();
        decreaseShipCounter(length);
        ships.add(ship);
        return true;
    }

    public boolean shootField(Field field) {
        Field boardField = getFieldFromTheBoard(field);
        if (boardField.isHit()) {
            throw new IllegalStateException("Field already shot");
        }
        boardField.markAsHit();
        if (!boardField.isPartOfTheShip()) {
            return true;
        }

        Ship hitShip = field.getShip();

        if (!hitShip.isDestroyed()) {
            return true;
        }

        markAdjacentFieldsAsHit(hitShip);
        return true;
    }

    private void markAdjacentFieldsAsHit(Ship hitShip) {
        Set<Field> fields = Ship.generateSetOfAdjacentFields(this, hitShip.occupiedFields);
        fields.forEach(Field::markAsHit);
    }

    public Set<Ship> getShips() {
        return ships;
    }

    // checks intersection of sets (with set of fields of other ships)
    private boolean validateSet(Set<Field> set) {
        Set<Field> intersectedFields = set.stream().filter(Field::isPartOfTheShip).collect(Collectors.toSet());
        return intersectedFields.size() == 0;
    }

    // checks if the type of ship with given length is still available
    private boolean isLengthAvailable(int length) {
        if (!availableShips.containsKey(length)) {
            throw new IllegalStateException("No such length");
        }

        return availableShips.get(length) > 0;
    }

    // lowers number of available ship type with given length
    private void decreaseShipCounter(int length) {
        if (!availableShips.containsKey(length)) {
            throw new IllegalStateException("No such length");
        }

        Integer counter = availableShips.get(length);

        if (counter < 1) {
            throw new IllegalStateException("No available length");
        }

        availableShips.put(length, --counter);
    }

    // generates set for the board (null if coordinates are reached)
    protected Set<Field> generateSetOfFieldsForShip(Field field, Orientation orientation, int length) {
        Set<Field> fields = new HashSet<>();

        while (length > 0) {
            Field fieldFromTheBoard = getFieldFromTheBoard(field);
            if (fieldFromTheBoard == null) {
                return null;
            }
            fields.add(fieldFromTheBoard);
            field = field.nextField(orientation);
            --length;
        }
        return fields;
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

    // allows to iterate over Fields
    @Override
    public Iterator<Field> iterator() {
        return board.iterator();
    }
}
