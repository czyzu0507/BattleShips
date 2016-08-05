package io.github.expansionteam.battleships.common.events;

public class ShootPositionEvent {

    public final Position position;

    public ShootPositionEvent(Position position) {
        this.position = position;
    }

    public static class Position {

        public final int x;
        public final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

}
