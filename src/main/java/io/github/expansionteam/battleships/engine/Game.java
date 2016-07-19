package io.github.expansionteam.battleships.engine;

import java.util.Iterator;
import java.util.Random;

public class Game {
    public void start() {
        Board board = new Board();
        generateRandomShips(board);
        // printTmp(board);
    }

    private void generateRandomShips(Board board) {
        Random random = new Random();
        int[] ships = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        for (int i = 0; i < ships.length; ) {
            Field field = new Field(random.nextInt(10), random.nextInt(10));
            Orientation orientation = resolveOrientation(random.nextInt(2));
            int length = ships[i];
            if (board.appendShip(field, orientation, length)) {
                i++;
            }
        }
    }

    private void printTmp(Board board) {
        Iterator<Field> iter = board.iterator();
        int n = 0;
        while (iter.hasNext()) {
            System.out.print(iter.next() + "  ");
            ++n;
            if (n % 10 == 0)
                System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        new Game().start();
    }

    private Orientation resolveOrientation(int randomInt) {
        if (randomInt % 2 == 0) {
            return Orientation.VERTICAL;
        } else {
            return Orientation.HORIZONTAL;
        }
    }

}
