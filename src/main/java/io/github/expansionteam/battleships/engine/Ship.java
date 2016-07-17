package io.github.expansionteam.battleships.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Ship {
    private final String name;
    protected final Set<Field> occupiedFields;

    // constructor
    private Ship(ShipBuilder shipBuilder) {
        this.name = shipBuilder.name;
        this.occupiedFields = shipBuilder.set;
        // pointers in fields !
        setPointers( this );
    }

    // set pointers
    private static void setPointers(Ship ship) {
        ship.occupiedFields.forEach( field -> field.setPointerToShip( ship ) );
    }

    // show the name
    public String showName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31 + occupiedFields.hashCode();
    }

    @Override
    public boolean equals(Object shipObject) {
        if (this == shipObject) return true;
        if ( shipObject == null || !(getClass().equals( shipObject.getClass() )) )
            return false;
        Ship ship = (Ship) shipObject;
        return name.equals( ship.name ) && occupiedFields.equals( ship.occupiedFields );
    }

    // helper builder-like class
    static class ShipBuilder {
        private final String name;
        private final Set<Field> set;

        // constructor
        ShipBuilder(Set<Field> setOfFields, int length) {
            name = map.get(length);
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
