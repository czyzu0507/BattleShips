package io.github.expansionteam.battleships.common.events.data;

public class ShipSizeData {

    private final int value;

    private ShipSizeData(int value) {
        this.value = value;
    }

    public static ShipSizeData of(int value) {
        return new ShipSizeData(value);
    }

    public int getValue() {
        return value;
    }

}
