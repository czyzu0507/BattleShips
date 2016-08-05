package io.github.expansionteam.battleships.common.events.data;

public class ShipSize {

    private final int value;

    private ShipSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
