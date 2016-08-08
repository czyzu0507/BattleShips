package io.github.expansionteam.battleships.common.events.data;

public class ShipData {

    private final PositionData position;
    private final ShipSizeData size;
    private final ShipOrientationData orientation;

    public ShipData(PositionData position, ShipSizeData size, ShipOrientationData orientation) {
        this.position = position;
        this.size = size;
        this.orientation = orientation;
    }

    public PositionData getPosition() {
        return position;
    }

    public ShipSizeData getSize() {
        return size;
    }

    public ShipOrientationData getOrientation() {
        return orientation;
    }

}
