package io.github.expansionteam.battleships.common.events.data;

public class ShipData {

    private final PositionData position;
    private final ShipSizeData shipSize;
    private final OrientationData orientation;

    public ShipData(PositionData position, ShipSizeData shipSize, OrientationData orientation) {
        this.position = position;
        this.shipSize = shipSize;
        this.orientation = orientation;
    }

    public PositionData getPosition() {
        return position;
    }

    public ShipSizeData getShipSize() {
        return shipSize;
    }

    public OrientationData getOrientation() {
        return orientation;
    }

}
