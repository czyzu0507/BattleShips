package io.github.expansionteam.battleships.engine;

import java.util.*;
import java.util.stream.Collectors;

class Ship {
    private final String name;
    protected final Set<Field> occupiedFields;

    private Ship(ShipBuilder shipBuilder) {
        this.name = shipBuilder.name;
        this.occupiedFields = shipBuilder.set;
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


    // helper builder-like class
    static class ShipBuilder {
        private final String name;
        private final Set<Field> set;

        ShipBuilder(Set<Field> setOfFields) {
            name = map.get(setOfFields.size());
            set = setOfFields;
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