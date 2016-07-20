package io.github.expansionteam.battleships.gui.models;

public enum ShipSize {

    ONE(1), TWO(2), THREE(3), FOUR(4);

    private final int value;

    ShipSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
