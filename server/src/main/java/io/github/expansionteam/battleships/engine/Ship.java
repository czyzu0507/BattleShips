package io.github.expansionteam.battleships.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Ship {
    private final String name;
    protected final Set<Field> occupiedFields;
    private final Field startField;
    private final Orientation orientation;
    private final int size;

    private Ship(ShipBuilder shipBuilder) {
        this.name = shipBuilder.name;
        this.occupiedFields = shipBuilder.set;
        this.startField = shipBuilder.startField;
        this.orientation = shipBuilder.orientation;
        this.size = shipBuilder.size;
        setParents(this);
    }

    private static void setParents(Ship ship) {
        ship.occupiedFields.forEach(field -> field.setParentShip(ship));
    }

    // set of adjacent fields
    static Set<Field> generateSetOfAdjacentFields(Board board, Set<Field> set) {
        Set<Field> tmpSet;   // set with nulls, adjacents, and ship's fields

        tmpSet = set.stream()
                .map(Field.FieldSetGenerator::createAllPossibleAdjacentFields)
                .flatMap(Collection::stream)
                .filter(e -> board.getFieldFromTheBoard(e) != null)
                .map(board::getFieldFromTheBoard)
                .filter(e -> !set.contains(e))
                .collect(Collectors.toSet());

        return tmpSet;
    }

    boolean isDestroyed() {
        return occupiedFields.stream().filter(Field::isHit).collect(Collectors.toSet()).size() == occupiedFields.size();
    }

    public Field getStartField() {
        return startField;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31 + occupiedFields.hashCode();
    }

    @Override
    public boolean equals(Object shipObject) {
        if (this == shipObject) return true;
        if (shipObject == null || !(getClass().equals(shipObject.getClass())))
            return false;
        Ship ship = (Ship) shipObject;
        return name.equals(ship.name) && occupiedFields.equals(ship.occupiedFields);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getSize() {
        return size;
    }

    // helper builder-like class
    static class ShipBuilder {
        private final String name;
        private final Set<Field> set;
        private Field startField;
        private Orientation orientation;
        private final int size;

        ShipBuilder(Set<Field> setOfFields, Field startField, Orientation orientation, int size) {
            name = map.get(setOfFields.size());
            set = setOfFields;
            this.startField = startField;
            this.orientation = orientation;
            this.size = size;
        }

        // helper map
        private static final Map<Integer, String> map = new HashMap<Integer, String>() {{
            put(1, "Submarine");
            put(2, "Destroyer");
            put(3, "Cruiser");
            put(4, "Battleship");
            put(5, "Aircraft carrier");
        }};

        Ship build() {
            return new Ship(this);
        }
    }
}