package io.github.expansionteam.battleships.common.events.data;

public class Ship {

    private final Position position;
    private final ShipSize shipSize;
    private final Orientation orientation;

    public Ship(Position position, ShipSize shipSize, Orientation orientation) {
        this.position = position;
        this.shipSize = shipSize;
        this.orientation = orientation;
    }

    public Position getPosition() {
        return position;
    }

    public ShipSize getShipSize() {
        return shipSize;
    }

    public Orientation getOrientation() {
        return orientation;
    }

}
