package io.github.expansionteam.battleships.common.events.data;

public class PositionData {

    private final int x;
    private final int y;

    private PositionData(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static PositionData of(int x, int y) {
        return new PositionData(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
