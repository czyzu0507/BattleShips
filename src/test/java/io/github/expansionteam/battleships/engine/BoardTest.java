package io.github.expansionteam.battleships.engine;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static io.github.expansionteam.battleships.engine.Orientation.HORIZONTAL;
import static io.github.expansionteam.battleships.engine.Orientation.VERTICAL;
import static org.testng.Assert.assertEquals;

@Test()
public class BoardTest {
    private final Board board = new Board();
    private final Board boardForSeqTest = new Board();
    private final Set<Field> boardSet = new TreeSet<>();

    @BeforeClass
    private void generateBoard() {
        board.forEach(boardSet::add);
    }

    @DataProvider(name = "positions")
    private Object[][] providePositions() {
        return new Object[][]{
                {new Field(-1, -1), false},
                {new Field(-1, 0), false},
                {new Field(0, 0), true},
                {new Field(0, 1), true},
                {new Field(1, 0), true},
                {new Field(9, 9), true},
                {new Field(9, 10), false},
                {new Field(10, 10), false}
        };
    }

    @DataProvider(name = "ofPosition")
    private Object[][] provideCoordinates() {
        return new Object[][]{
                {new Field(1, 1), new Field(1, 1)},
                {new Field(1, 2), new Field(1, 2)},
                {new Field(-1, 0), null},
                {new Field(-1, -2), null}
        };
    }

    @DataProvider(name = "setOfShipFields")
    private Object[][] provideDataToTestPointers() {
        Field startingField1 = new Field(-1, -1);
        Field startingField2 = new Field(0, 0);
        Field startingField3 = new Field(9, 9);

        return new Object[][]{
                {startingField1, HORIZONTAL, 3, null},
                {startingField1, VERTICAL, 1, null},
                {startingField2, HORIZONTAL, 2, new HashSet<>(Arrays.asList(new Field[]{new Field(0, 0), new Field(1, 0)}))},
                {startingField2, VERTICAL, 2, new HashSet<>(Arrays.asList(new Field[]{new Field(0, 0), new Field(0, 1)}))},
                {startingField3, HORIZONTAL, 1, new HashSet<>(Arrays.asList(new Field[]{new Field(9, 9)}))},
                {startingField3, VERTICAL, 1, new HashSet<>(Arrays.asList(new Field[]{new Field(9, 9)}))},
                {startingField3, HORIZONTAL, 2, null},
                {startingField3, VERTICAL, 2, null}
        };
    }

    @DataProvider(name = "appendShips")
    private Object[][] provideAppendShipsData() {
        return new Object[][]{
                {new Field(1, 1), HORIZONTAL, 4, new HashSet<>(Arrays.asList(new Field[]{new Field(1, 1), new Field(2, 1), new Field(3, 1), new Field(4, 1)}))},
                {new Field(1, 0), VERTICAL, 2, null},
                {new Field(7, 0), HORIZONTAL, 4, null},
                {new Field(7, 0), VERTICAL, 2, new HashSet<>(Arrays.asList(new Field[]{new Field(7, 0), new Field(7, 1)}))},
                {new Field(7, 1), VERTICAL, 4, null},
                {new Field(3, 6), VERTICAL, 3, new HashSet<>(Arrays.asList(new Field[]{new Field(3, 6), new Field(3, 7), new Field(3, 8)}))}
        };
    }

    @DataProvider(name = "appendShipsAdjacent")
    private Object[][] provideAppendShipsWithoutAdjacentData() {
        Board b = new Board();
        return new Object[][]{
                {b.appendShip(new Field(1, 1), HORIZONTAL, 4), true},
                {b.appendShip(new Field(5, 0), VERTICAL, 3), false},
                {b.appendShip(new Field(3, 6), VERTICAL, 3), true},
                {b.appendShip(new Field(2, 0), VERTICAL, 2), false},
                {b.appendShip(new Field(7, 7), VERTICAL, 1), true}
        };
    }

    @DataProvider(name = "appendAvailableShips")
    private Object[][] provideDataToTestAvailableShips() {
        Board board = new Board();
        return new Object[][]{
                {board.appendShip(new Field(0, 0), HORIZONTAL, 4), true},
                {board.appendShip(new Field(0, 2), HORIZONTAL, 4), false},

                {board.appendShip(new Field(0, 4), HORIZONTAL, 3), true},
                {board.appendShip(new Field(0, 6), HORIZONTAL, 3), true},
                {board.appendShip(new Field(0, 8), HORIZONTAL, 3), false},

                {board.appendShip(new Field(5, 3), HORIZONTAL, 2), true},
                {board.appendShip(new Field(5, 5), HORIZONTAL, 2), true},
                {board.appendShip(new Field(5, 7), HORIZONTAL, 2), true},
                {board.appendShip(new Field(5, 9), HORIZONTAL, 2), false},

                {board.appendShip(new Field(9, 0), HORIZONTAL, 1), true},
                {board.appendShip(new Field(9, 2), HORIZONTAL, 1), true},
                {board.appendShip(new Field(9, 4), HORIZONTAL, 1), true},
                {board.appendShip(new Field(9, 6), HORIZONTAL, 1), true},
                {board.appendShip(new Field(9, 8), HORIZONTAL, 1), false},
        };
    }

    @Test(dataProvider = "positions")
    public void testIfBoardContainsParticularField(Field field, boolean expected) {
        assertEquals(boardSet.contains(field), expected);
    }

    @Test(dataProvider = "ofPosition")
    public void testGetFieldOfPosition(Field toFind, Field expected) {
        assertEquals(board.getFieldFromTheBoard(toFind), expected);
    }

    @Test(dataProvider = "setOfShipFields")
    public void testReturnedSetsOfFields(Field startingField, Orientation orientation, int length, Set<Field> set) {
        // when
        Set<Field> actual = board.generateSetOfFieldsForShip(startingField, orientation, length);
        // then
        assertEquals(actual, set);
    }

    @Test(dataProvider = "appendShips")
    public void testSequentiallyAppendShips(Field field, Orientation orientation, int length, Set<Field> expected) {
        // when
        boolean appended = boardForSeqTest.appendShip(field, orientation, length);
        Set<Ship> ships = boardForSeqTest.getShips();
        Set<Field> actual = null;
        for (Ship ship : ships) {
            if (appended) {
                actual = ship.occupiedFields;
            }
        }
        // then
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "appendShipsAdjacent")
    public void testAdjacentAppendingShips(boolean actual, boolean expected) {
        // then
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "appendAvailableShips")
    public void testIfShipIsAvailable(boolean addsShip, boolean expected) {
        // then
        assertEquals(addsShip, expected);
    }
}
