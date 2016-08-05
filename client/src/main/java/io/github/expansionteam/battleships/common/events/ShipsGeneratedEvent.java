package io.github.expansionteam.battleships.common.events;

import java.util.ArrayList;
import java.util.List;

public class ShipsGeneratedEvent {

    public final List<Ship> ships = new ArrayList<>();

    public static class Ship {

        public final Position position;
        public final int size;
        public final Orientation orientation;

        public Ship(Position position, int size, Orientation orientation) {
            this.position = position;
            this.size = size;
            this.orientation = orientation;
        }

        public static class Position {

            public final int x;
            public final int y;

            public Position(int x, int y) {
                this.x = x;
                this.y = y;
            }

        }

        public enum Orientation {

            HORIZONTAL, VERTICAL;

        }

    }

}
